![img1](/web/resources/logo/logo.png)
* * *
## UST CICS ICS2609 2CSB Group 5 - Final Academic Project
Members:
* De Leon, Elijah John
* Fernandez, Aaron Jacob
* Fernando, Kenneth Christopher
***
## Responsive Web Pages
* Home Page (index.jsp)
* Login Page (login.jsp)
* Register Page (register.jsp)
* Cart Page (cart.jsp)
* Success Purchase Page (success_page_buy.jsp)
* * *
## Error Handling using web.xml and HTTP Status Codes
* 404 (missing_page_buy.html)
* 420 (cart_error.html)
* 440 (login_error.html)
* 500 (content_error.html)
***
## Libraries
1. commons-codec-1.9.jar
2. derbyclient.jar
3. itextpdf-5.5.9.jar
4. simplecaptcha-1.2.1.jar
5. gson-2.10.1.jar
***
## Databases
<details>
    <summary>Products Database</summary>
    
### CREATE SCRIPT
```sql
CREATE TABLE PRODUCTS (PROD_ID INTEGER NOT NULL, "NAME" VARCHAR(255) NOT NULL, PRICE DOUBLE NOT NULL, STOCK INTEGER NOT NULL, "SIZE" VARCHAR(255), CATEGORY VARCHAR(255), IMAGE VARCHAR(255));
```
### INSERT SCRIPT
```sql
INSERT INTO APP.PRODUCTS (PROD_ID, "NAME", PRICE, STOCK, "SIZE", CATEGORY, IMAGE) 
	VALUES (0, 'Demon Slayer: Kimetsu No Yaiba Shinobu T-shirt', 28.9, 219, 'XS/S/M/MD/LG/XL/2X/3X', 'T-Shirt', 'tshirt_shinobu.jpg');
INSERT INTO APP.PRODUCTS (PROD_ID, "NAME", PRICE, STOCK, "SIZE", CATEGORY, IMAGE) 
	VALUES (1, 'Demon Slayer: Kimetsu No Yaiba Kimono Chopstick Set', 19.9, 91, 'N/A', 'Utensil', 'kimetsu_chopstick.jpg');
INSERT INTO APP.PRODUCTS (PROD_ID, "NAME", PRICE, STOCK, "SIZE", CATEGORY, IMAGE) 
	VALUES (2, 'Demon Slayer: Kimetsu No Yaiba Bag Character Key Chain', 8.9, 165, 'N/A', 'Bag', 'kny_keychain.jpg');
INSERT INTO APP.PRODUCTS (PROD_ID, "NAME", PRICE, STOCK, "SIZE", CATEGORY, IMAGE) 
	VALUES (3, 'Demon Slayer: Kimetsu No Yaiba Nezuko T-Shirt', 28.9, 86, 'XS/S/M/MD/LG/XL/2X/3X', 'T-Shirt', 'tshirt_nezuko.jpg');
INSERT INTO APP.PRODUCTS (PROD_ID, "NAME", PRICE, STOCK, "SIZE", CATEGORY, IMAGE) 
	VALUES (4, 'Demon Slayer: Kimetsu No Yaiba Group Black & White Panel T-Shirt', 27.9, 133, 'XS/S/M/MD/LG/XL/2X/3X', 'T-Shirt', 'tshirt_kny_mcs.jpg');
INSERT INTO APP.PRODUCTS (PROD_ID, "NAME", PRICE, STOCK, "SIZE", CATEGORY, IMAGE) 
	VALUES (5, 'Demon Slayer: Kimetsu No Yaiba Rengoku T-Shirt', 28.9, 107, 'XS/S/M/MD/LG/XL/2X/3X', 'T-Shirt', 'tshirt_rengoku.jpg');
INSERT INTO APP.PRODUCTS (PROD_ID, "NAME", PRICE, STOCK, "SIZE", CATEGORY, IMAGE) 
	VALUES (6, 'Demon Slayer: Kimetsu No Yaiba Mitsuri T-Shirt', 25.9, 148, 'XS/S/M/MD/LG/XL/2X', 'T-Shirt', 'tshirt_mitsuri.jpg');
INSERT INTO APP.PRODUCTS (PROD_ID, "NAME", PRICE, STOCK, "SIZE", CATEGORY, IMAGE) 
	VALUES (7, 'Demon Slayer: Kimetsu No Yaiba Entertainment District Arc Poster T-Shirt', 25.9, 196, 'XS/S/M/MD/LG/XL/2X', 'T-Shirt', 'tshirt_poster_kny.jpg');
INSERT INTO APP.PRODUCTS (PROD_ID, "NAME", PRICE, STOCK, "SIZE", CATEGORY, IMAGE) 
	VALUES (8, 'Demon Slayer: Kimetsu No Yaiba Giyu Tomioka T-Shirt', 27.9, 275, 'XS/S/M/MD/LG/XL/2X/3X', 'T-Shirt', 'tshirt_giyu.jpg');
INSERT INTO APP.PRODUCTS (PROD_ID, "NAME", PRICE, STOCK, "SIZE", CATEGORY, IMAGE) 
	VALUES (9, 'Attack On Titan Scout Regiment Hoodie', 46.9, 172, 'SM/MD/LG/2X', 'Hoodie', 'hoodie_aot.jpg');
INSERT INTO APP.PRODUCTS (PROD_ID, "NAME", PRICE, STOCK, "SIZE", CATEGORY, IMAGE) 
	VALUES (10, 'Attack On Titan Mikasa Manga Panel T-Shirt', 27.9, 58, 'XS/SM/MD/LG/XL/2X/3X', 'T-Shirt', 'tshirt_mikasa.jpg');
INSERT INTO APP.PRODUCTS (PROD_ID, "NAME", PRICE, STOCK, "SIZE", CATEGORY, IMAGE) 
	VALUES (11, 'Attack On Titan Eren Attacks Marley T-Shirt', 25.9, 143, 'XS/SM/MD/LG/XL/2X', 'T-Shirt', 'tshirt_marley.jpg');
INSERT INTO APP.PRODUCTS (PROD_ID, "NAME", PRICE, STOCK, "SIZE", CATEGORY, IMAGE) 
	VALUES (12, 'Attack On Titan Character Bag Key Chain', 8.9, 245, 'N/A', 'Collectible', 'aot_keychain.jpg');
INSERT INTO APP.PRODUCTS (PROD_ID, "NAME", PRICE, STOCK, "SIZE", CATEGORY, IMAGE) 
	VALUES (13, 'Attack On Titan Ramen Bowl With Chopsticks', 19.9, 183, 'N/A', 'Utensil', 'aot_bowl.jpg');
INSERT INTO APP.PRODUCTS (PROD_ID, "NAME", PRICE, STOCK, "SIZE", CATEGORY, IMAGE) 
	VALUES (14, 'Attack On Titan Attack Titan VS Armor Titan T-Shirt', 27.9, 103, 'XS/SM/MD/LG/XL/2X/3X', 'T-Shirt', 'tshirt_attack_armor.jpg');
INSERT INTO APP.PRODUCTS (PROD_ID, "NAME", PRICE, STOCK, "SIZE", CATEGORY, IMAGE) 
	VALUES (15, 'Attack On Titan Beast Titan Battle T-Shirt', 27.9, 215, 'XS/SM/MD/LG/XL/2X/3X', 'T-Shirt', 'tshirt_beast.jpg');
INSERT INTO APP.PRODUCTS (PROD_ID, "NAME", PRICE, STOCK, "SIZE", CATEGORY, IMAGE) 
	VALUES (16, 'Attack On Titan Regiment Crests Mini Backpack', 49.9, 232, 'N/A', 'Bag', 'bag_aot.jpg');
INSERT INTO APP.PRODUCTS (PROD_ID, "NAME", PRICE, STOCK, "SIZE", CATEGORY, IMAGE) 
	VALUES (17, 'Attack On Titan Scout Regiment Sling Bag', 44.9, 241, 'N/A', 'Bag', 'slingbag_aot.jpg');
INSERT INTO APP.PRODUCTS (PROD_ID, "NAME", PRICE, STOCK, "SIZE", CATEGORY, IMAGE) 
	VALUES (18, 'Attack On Titan Female & Armor Titan Water Bottle', 16.9, 253, 'N/A', 'Utensil', 'bottle_aot.jpg');
INSERT INTO APP.PRODUCTS (PROD_ID, "NAME", PRICE, STOCK, "SIZE", CATEGORY, IMAGE) 
	VALUES (19, 'Jujutsu Kaisen Gojo Satoru Laying Down Plush', 23.92, 59, 'N/A', 'Collectible', '17538362_hi.jpg');
INSERT INTO APP.PRODUCTS (PROD_ID, "NAME", PRICE, STOCK, "SIZE", CATEGORY, IMAGE) 
	VALUES (20, 'Toilet-Bound Hanako-Kun Duo Snapback Hat', 9.49, 78, 'N/A', 'Cap', '18171496_hi.jpg');
INSERT INTO APP.PRODUCTS (PROD_ID, "NAME", PRICE, STOCK, "SIZE", CATEGORY, IMAGE) 
	VALUES (21, 'My Hero Academia Chibi Mini Backpack', 24.95, 117, 'N/A', 'Bag', '18072047_hi.jpg');
INSERT INTO APP.PRODUCTS (PROD_ID, "NAME", PRICE, STOCK, "SIZE", CATEGORY, IMAGE) 
	VALUES (22, 'Loungefly Pokemon Eevee Sakura Mini Backpack', 43.92, 219, 'N/A', 'Bag', '19632542_hi.jpg');
INSERT INTO APP.PRODUCTS (PROD_ID, "NAME", PRICE, STOCK, "SIZE", CATEGORY, IMAGE) 
	VALUES (23, 'Studio Ghibli My Neighbor Totoro Floral Slouch Mini Backpack', 35.92, 195, 'N/A', 'Bag', '20096088_hi.jpg');
INSERT INTO APP.PRODUCTS (PROD_ID, "NAME", PRICE, STOCK, "SIZE", CATEGORY, IMAGE) 
	VALUES (24, 'Funko Jujutsu Kaisen Pop! Animation Satoru Gojo (Unmasked) Vinyl Figure 2023 Wondrous Convention Exclusive', 14.9, 186, 'N/A', 'Collectible', '20079769_hi.jpg');
INSERT INTO APP.PRODUCTS (PROD_ID, "NAME", PRICE, STOCK, "SIZE", CATEGORY, IMAGE) 
	VALUES (25, 'Funko InuYasha Pop! Animation Sango Vinyl Figure Hot Topic Exclusive', 14.9, 151, 'N/A', 'Collectible', '20193472_hi.jpg');
INSERT INTO APP.PRODUCTS (PROD_ID, "NAME", PRICE, STOCK, "SIZE", CATEGORY, IMAGE) 
	VALUES (26, 'Funko My Hero Academia Pop! Animation Fumikage Tokoyami Vinyl Figure Hot Topic Exclusive', 14.9, 263, 'N/A', 'Collectible', '19799544_hi.jpg');
INSERT INTO APP.PRODUCTS (PROD_ID, "NAME", PRICE, STOCK, "SIZE", CATEGORY, IMAGE) 
	VALUES (27, 'Studio Ghibli Spirited Away Soot Sprites Candy Drop Earrings', 9.9, 158, 'N/A', 'Collectible', '16816888_hi.jpg');
INSERT INTO APP.PRODUCTS (PROD_ID, "NAME", PRICE, STOCK, "SIZE", CATEGORY, IMAGE) 
	VALUES (28, 'One Piece Freeny''s Hidden Dissectibles Series 4 Blind Box Figure', 11.92, 117, 'N/A', 'Collectible', '19903639_hi.jpg');
INSERT INTO APP.PRODUCTS (PROD_ID, "NAME", PRICE, STOCK, "SIZE", CATEGORY, IMAGE) 
	VALUES (29, 'Fruits Basket X Hello Kitty And Friends Chibi Blind Box Enamel Pin', 8.9, 172, 'N/A', 'Collectible', '18862869_hi.jpg');
INSERT INTO APP.PRODUCTS (PROD_ID, "NAME", PRICE, STOCK, "SIZE", CATEGORY, IMAGE) 
	VALUES (30, 'Sailor Moon Trio Cosmic Heart Compact Mini Backpack', 29.94, 298, 'N/A', 'Bag', '17780336_hi.jpg');
INSERT INTO APP.PRODUCTS (PROD_ID, "NAME", PRICE, STOCK, "SIZE", CATEGORY, IMAGE) 
	VALUES (31, 'Funko Attack On Titan Pop! Animation Zeke Yeager Vinyl Figure Hot Topic Exclusive', 10.43, 141, 'N/A', 'Collectible', '19943638_hi.jpg');
INSERT INTO APP.PRODUCTS (PROD_ID, "NAME", PRICE, STOCK, "SIZE", CATEGORY, IMAGE) 
	VALUES (32, 'Pokemon Ghost Type Mini Backpack', 39.92, 59, 'N/A', 'Bag', '19960510_hi.jpg');
INSERT INTO APP.PRODUCTS (PROD_ID, "NAME", PRICE, STOCK, "SIZE", CATEGORY, IMAGE) 
	VALUES (33, 'Inuyasha Sesshomaru Portrait Boyfriend Fit Girls T-Shirt', 19.92, 88, 'XS/SM/MD/LG/XL', 'T-Shirt', '20626169_hi.jpg');
INSERT INTO APP.PRODUCTS (PROD_ID, "NAME", PRICE, STOCK, "SIZE", CATEGORY, IMAGE) 
	VALUES (34, 'Soul Eater Soul & Death The Kid Hoodie', 39.92, 101, 'XS/SM/MD/LG/XL/2X', 'Hoodie', '20253425_hi.jpg');
INSERT INTO APP.PRODUCTS (PROD_ID, "NAME", PRICE, STOCK, "SIZE", CATEGORY, IMAGE) 
	VALUES (35, 'Chainsaw Man Hoodie', 39.92, 240, 'SM/MD/LG/XL/2X/3X', 'Hoodie', '20105107_hi.jpg');
INSERT INTO APP.PRODUCTS (PROD_ID, "NAME", PRICE, STOCK, "SIZE", CATEGORY, IMAGE) 
	VALUES (36, 'My Hero Academia Bakugo Hero Costume Cosplay Hoodie', 35.39, 96, 'XS/SM/MD', 'Hoodie', '18799955_hi.jpg');
INSERT INTO APP.PRODUCTS (PROD_ID, "NAME", PRICE, STOCK, "SIZE", CATEGORY, IMAGE) 
	VALUES (37, 'Tokyo Ghoul Falling Hoodie', 35.92, 300, 'SM/LG/XL/2X/3X', 'Hoodie', '18638003_hi.jpg');
INSERT INTO APP.PRODUCTS (PROD_ID, "NAME", PRICE, STOCK, "SIZE", CATEGORY, IMAGE) 
	VALUES (38, 'Studio Ghibli Ponyo Color-Block Girls Hoodie Plus Size', 43.92, 183, 'SM/LG/XL/2X/3X', 'Hoodie', '19942241_hi.jpg');
INSERT INTO APP.PRODUCTS (PROD_ID, "NAME", PRICE, STOCK, "SIZE", CATEGORY, IMAGE) 
	VALUES (39, 'One Piece Film: Red Uta Pink Hoodie', 43.92, 165, 'XS/SM/MD/LG/XL', 'Hoodie', '20155762_hi.jpg');
INSERT INTO APP.PRODUCTS (PROD_ID, "NAME", PRICE, STOCK, "SIZE", CATEGORY, IMAGE) 
	VALUES (40, 'One Piece Film: Red Shanks Outline Hoodie', 43.92, 178, 'XS/SM/MD/LG', 'Hoodie', '20155772_hi.jpg');
INSERT INTO APP.PRODUCTS (PROD_ID, "NAME", PRICE, STOCK, "SIZE", CATEGORY, IMAGE) 
	VALUES (41, 'One Piece Luffy Punch Hoodie', 39.92, 284, 'SM/MD/LG/XL/2X/3X', 'Hoodie', '20089293_hi.jpg');
INSERT INTO APP.PRODUCTS (PROD_ID, "NAME", PRICE, STOCK, "SIZE", CATEGORY, IMAGE) 
	VALUES (42, 'Bleach Red Trio Hoodie', 35.92, 256, 'XS/SM/MD/2X/3X', 'Hoodie', '20137667_hi.jpg');
INSERT INTO APP.PRODUCTS (PROD_ID, "NAME", PRICE, STOCK, "SIZE", CATEGORY, IMAGE) 
	VALUES (43, 'Attack On Titan Armored Titan Metal Sweatshirt', 31.92, 268, 'XS/SM', 'Hoodie', '19001068_hi.jpg');
INSERT INTO APP.PRODUCTS (PROD_ID, "NAME", PRICE, STOCK, "SIZE", CATEGORY, IMAGE) 
	VALUES (44, 'Tokyo Ghoul Tie-Dye Girls Hoodie', 24.49, 72, 'SM', 'Hoodie', '19031664_hi.jpg');
INSERT INTO APP.PRODUCTS (PROD_ID, "NAME", PRICE, STOCK, "SIZE", CATEGORY, IMAGE) 
	VALUES (45, 'Spy X Family Anya Patch Dad Cap', 18.32, 267, 'N/A', 'Cap', '19888213_hi.jpg');
INSERT INTO APP.PRODUCTS (PROD_ID, "NAME", PRICE, STOCK, "SIZE", CATEGORY, IMAGE) 
	VALUES (46, 'Pokemon Pikachu Peeking 3D Ears Bucket Hat', 19.92, 222, 'N/A', 'Cap', '20299782_hi.jpg');
INSERT INTO APP.PRODUCTS (PROD_ID, "NAME", PRICE, STOCK, "SIZE", CATEGORY, IMAGE) 
	VALUES (47, 'Junji Ito Faces Bucket Hat', 24.9, 279, 'N/A', 'Cap', '20476071_hi.jpg');
INSERT INTO APP.PRODUCTS (PROD_ID, "NAME", PRICE, STOCK, "SIZE", CATEGORY, IMAGE) 
	VALUES (48, 'Kuromi 3D Ears Bucket Hat', 19.92, 78, 'N/A', 'Cap', '19888503_hi.jpg');
INSERT INTO APP.PRODUCTS (PROD_ID, "NAME", PRICE, STOCK, "SIZE", CATEGORY, IMAGE) 
	VALUES (49, 'Spy X Family Anya Forger Bucket Hat', 19.92, 190, 'N/A', 'Cap', '19886725_hi.jpg');
INSERT INTO APP.PRODUCTS (PROD_ID, "NAME", PRICE, STOCK, "SIZE", CATEGORY, IMAGE) 
	VALUES (50, 'My Neighbor Totoro Corduroy Flower Bucket Hat', 18.32, 231, 'N/A', 'Cap', '17464874_hi.jpg');
INSERT INTO APP.PRODUCTS (PROD_ID, "NAME", PRICE, STOCK, "SIZE", CATEGORY, IMAGE) 
	VALUES (51, 'My Hero Academia Bakugo Split Snapback Hat', 18.32, 55, 'N/A', 'Cap', '19759552_hi.jpg');
INSERT INTO APP.PRODUCTS (PROD_ID, "NAME", PRICE, STOCK, "SIZE", CATEGORY, IMAGE) 
	VALUES (52, 'Bananya 3D Ears Dad Cap', 10.99, 237, 'N/A', 'Cap', '19826529_hi.jpg');
INSERT INTO APP.PRODUCTS (PROD_ID, "NAME", PRICE, STOCK, "SIZE", CATEGORY, IMAGE) 
	VALUES (53, 'Kuromi Pink Flames Trucker Hat', 15.92, 101, 'N/A', 'Cap', '19760212_hi.jpg');
INSERT INTO APP.PRODUCTS (PROD_ID, "NAME", PRICE, STOCK, "SIZE", CATEGORY, IMAGE) 
	VALUES (54, 'Studio Ghibli My Neighbor Totoro Umbrella Patch Dad Cap', 18.32, 94, 'N/A', 'Cap', '16069652_hi.jpg');
INSERT INTO APP.PRODUCTS (PROD_ID, "NAME", PRICE, STOCK, "SIZE", CATEGORY, IMAGE) 
	VALUES (55, 'Naruto Shippuden Itachi Flames Snapback Hat', 18.32, 230, 'N/A', 'Cap', '20536388_hi.jpg');
INSERT INTO APP.PRODUCTS (PROD_ID, "NAME", PRICE, STOCK, "SIZE", CATEGORY, IMAGE) 
	VALUES (56, 'Junji Ito Anime Panel Snapback Hat', 18.32, 220, 'N/A', 'Cap', '20476216_hi.jpg');
```
</details>

<details>
    <summary>Users Database</summary>

### CREATE SCRIPT
```sql
CREATE TABLE USERS (USER_ID INTEGER NOT NULL, USERNAME VARCHAR(30) NOT NULL, EMAIL VARCHAR(100) NOT NULL, PASSWORD VARCHAR(255) NOT NULL, CART VARCHAR(32672), PRIMARY KEY (USER_ID));
```
### INSERT SCRIPT
```sql
INSERT INTO APP.USERS (USER_ID, USERNAME, EMAIL, PASSWORD, CART) 
	VALUES (1, 'Dummy1', 'dummy1@gmail.com', '9COiVoB+pUxvSZT7Tci9og==', '{"owner":"Dummy1","cartItemMap":[[{"productId":1,"size":"S"},1],[{"productId":3,"size":"L"},3],[{"productId":4,"size":"XL"},5],[{"productId":5,"size":"L"},8]]}');
```
</details>

<details>
    <summary>Transactions Database</summary>

### CREATE SCRIPT
```sql
CREATE TABLE TRANSACTIONS (TRAN_ID INTEGER NOT NULL, ITEMS VARCHAR(32672) NOT NULL, TOTAL DOUBLE NOT NULL, DATETIME TIMESTAMP NOT NULL, PRIMARY KEY (TRAN_ID));
```
### INSERT SCRIPT
```sql
INSERT INTO APP.TRANSACTIONS (TRAN_ID, ITEMS, TOTAL, DATETIME) 
	VALUES (0, '{"owner":"Dummy1","cartItemMap":[[{"productId":4,"size":"XL"},5]]}', 139.5, '2023-05-13 19:22:20.11');
INSERT INTO APP.TRANSACTIONS (TRAN_ID, ITEMS, TOTAL, DATETIME) 
	VALUES (1, '{"owner":"Dummy1","cartItemMap":[[{"productId":1,"size":"S"},1]]}', 19.9, '2023-05-13 19:24:16.095');
```
</details>

***
