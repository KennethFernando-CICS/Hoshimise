package controller;

import com.google.gson.Gson;
import javax.servlet.*;
import javax.servlet.http.*;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import com.itextpdf.text.Font;
import java.io.*;
import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import model.Cart;
import model.CartItem;

public class ReceiptServlet extends HttpServlet {
    Connection conn = null;
    PreparedStatement ps = null;
    ResultSet rs = null;
    RequestDispatcher rd = null;
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/pdf");
        OutputStream out = response.getOutputStream();
        HttpSession session = request.getSession();
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss"); //format for the local date
        String purchaseTime = dtf.format(LocalDateTime.now());
        double total = 0.00;
        Cart itemsBought = null;
        int tran_id = Integer.parseInt(request.getParameter("tran_id"));
        try {
            conn = connectDB(getServletContext());
            String query = "SELECT * FROM TRANSACTIONS WHERE TRAN_ID = ?";
            ps = conn.prepareStatement(query);
            ps.setInt(1, tran_id);
            rs = ps.executeQuery();
            if(rs.next()){
                total = rs.getDouble("TOTAL");
                Timestamp timeOfPurchase = rs.getTimestamp("DATETIME");
                purchaseTime = dtf.format(timeOfPurchase.toLocalDateTime());
                itemsBought = new Gson().fromJson(rs.getString("ITEMS"), Cart.class);
            }
            //Fonts
            Font[] f = {
                new Font(Font.FontFamily.TIMES_ROMAN, 15, Font.BOLD), //Thank you message font
                new Font(Font.FontFamily.TIMES_ROMAN, 17), //Total Font
                new Font(Font.FontFamily.TIMES_ROMAN, 13, Font.ITALIC), //Description and Price font
                new Font(Font.FontFamily.TIMES_ROMAN, 10, Font.ITALIC)
            };
            
            //Item list table
            PdfPTable itemList = new PdfPTable(2);
            Paragraph item = new Paragraph("Description", f[2]);
            itemList.getDefaultCell().setBorder(0);
            PdfPCell price = new PdfPCell(new Paragraph("Price", f[2]));
            price.setHorizontalAlignment(Element.ALIGN_RIGHT);
            price.setBorder(PdfPCell.NO_BORDER);
            itemList.addCell(item);
            itemList.addCell(price);
            
            Map<String,String> itemMap = getProductDetails(itemsBought);                            
            for (Map.Entry<String, String> entry: itemMap.entrySet()) {
                itemList.addCell(entry.getKey());
                price = new PdfPCell(new Paragraph(entry.getValue()));
                price.setHorizontalAlignment(Element.ALIGN_RIGHT);
                price.setBorder(PdfPCell.NO_BORDER);
                itemList.addCell(price);
            }    
            itemList.setTotalWidth(220);
            float itemListHeight = (float) Math.ceil(itemList.getTotalHeight()/10) * 10;//For Dynamic Height based on product list
           
            //iTextPDF Setup
            Rectangle rec = new Rectangle(Utilities.inchesToPoints(4), Utilities.inchesToPoints(7) + itemListHeight);
            Document doc = new Document(rec);
            
            PdfWriter writer = PdfWriter.getInstance(doc, out);

            float vMargin = Utilities.inchesToPoints((float) 0.4);
            float hMargin = Utilities.inchesToPoints((float) 0.1);
            doc.setMargins(hMargin, hMargin, vMargin, 0);

            //PDF Writing
            doc.open();
            //Paragraphs
            Paragraph divider = new Paragraph("----------------------------------------------------------------"),
                    tyMessage = new Paragraph("Thank you for shopping with us!", f[0]),
                    header = new Paragraph("CASH RECIEPT", f[0]),
                    user = new Paragraph("Customer: " + session.getAttribute("username"), f[3]),
                    time = new Paragraph("Time of Purchase: " + purchaseTime, f[3]);
            divider.setAlignment(Paragraph.ALIGN_CENTER);
            tyMessage.setAlignment(Paragraph.ALIGN_CENTER);
            header.setAlignment(Paragraph.ALIGN_CENTER);
            time.setAlignment(Paragraph.ALIGN_CENTER);
            user.setAlignment(Paragraph.ALIGN_CENTER);

            //logo
            String relativeWebPath = "/resources/logo/rLogo.png";
            String absoluteDiskPath = getServletContext().getRealPath(relativeWebPath);
            Image img = Image.getInstance(absoluteDiskPath);
            img.setAlignment(Element.ALIGN_CENTER);
            doc.add(img);

            doc.add(divider);

            //includes header and time generated
            doc.add(header);
            doc.add(time);
            doc.add(user);

            doc.add(divider);
            
            //Add item list
            doc.add(itemList);

            doc.add(divider);

            //Total Cost
            PdfPTable totalTable = new PdfPTable(2);
            totalTable.getDefaultCell().setBorder(0);

            //Total price
            totalTable.addCell(new Paragraph("Total", f[1]));
            PdfPCell totalPrice = new PdfPCell(new Paragraph("$" + String.format("%.2f", total), f[1]));
            totalPrice.setHorizontalAlignment(Element.ALIGN_RIGHT);
            totalPrice.setBorder(PdfPCell.NO_BORDER);
            totalTable.addCell(totalPrice);
            doc.add(totalTable);

            doc.add(divider);

            //Thank you message
            doc.add(tyMessage);
            doc.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {            
            try{
                out.close();
                if(rs != null)
                    rs.close();
                if(ps != null)
                    rs.close();
                if(conn != null)
                    conn.close();
            } catch(SQLException e){}
        }
    }

    /**
     * Gets connection from database
     * @param sc ServletContext
     * @return Connection
     */
    public Connection connectDB(ServletContext sc){
        Connection conn = null;       
        try {
            String driver = sc.getInitParameter("jdbcClassName");
            Class.forName(driver);
            
            String url = sc.getInitParameter("jdbcDriverURL");
            String username = sc.getInitParameter("dbUserName");
            String password = sc.getInitParameter("dbPassword");
            conn = DriverManager.getConnection(url, username, password);
        }
        catch (Exception e){
            e.printStackTrace();
        }        
        return conn;
    }
    
    /**
     * Gets item details needed to be shown on receipt
     * @param itemCart of bought items
     * @return map containing item name and item total price
     */
    public Map<String,String> getProductDetails(Cart itemCart){
        Map<String, String> printMap = new HashMap<>();
        for (Map.Entry<CartItem, Integer> entry : itemCart.getCartItemMap().entrySet()) {            
            try {
                CartItem item = entry.getKey();
                rs = getProductDetails(item.getProductId());
                rs.next();
                String name = rs.getString("NAME") + " - " + item.getSize() + " x" + entry.getValue();
                double price = rs.getDouble("PRICE") * entry.getValue();
                printMap.put(name, String.format("%.2f", price));
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        return printMap;        
    }
    
    /**
     * Get product details from database using id
     * @param id of product
     * @return ResultSet with product details
     */
    public ResultSet getProductDetails(int id){
        try {
            String query = "SELECT NAME,PRICE FROM PRODUCTS WHERE PROD_ID = ?";
            ps = conn.prepareStatement(query);
            ps.setInt(1, id);
            
            rs = ps.executeQuery();
            return rs;
        } catch (SQLException ex) {
            ex.printStackTrace();
        }       
        return null;
    }
    
    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
