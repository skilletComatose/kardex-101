INSERT INTO categories (category_id, name, description) VALUES (1, 'Camisetas', 'Camisetas de superhéroes');
INSERT INTO categories (category_id, name, description) VALUES (2, 'Vasos', 'Vasos temáticos de superhéroes');
INSERT INTO categories (category_id, name, description) VALUES (3, 'Comics', 'Comics de Marvel y DC');

INSERT INTO products (product_id, name, description, price, stock_quantity, category_id, image_url)
VALUES (98, 'Camiseta Spider-Man', 'Camiseta con diseño de Spider-Man', 19.99, 100, 1, 'https://moviesshopco.vtexassets.com/arquivos/ids/174740-1600-1600?v=638291946601600000&width=1600&height=1600&aspect=true');

INSERT INTO products (product_id, name, description, price, stock_quantity, category_id, image_url)
VALUES (99, 'Vaso Batman', 'Vaso con diseño de Batman', 9.99, 200, 2 , 'https://www.arkhamcoffeeandcomics.com/wp-content/uploads/2020/05/61seOLX9HDL._AC_SX466_.jpg');

INSERT INTO products (product_id, name, description, price, stock_quantity, category_id, image_url)
VALUES (10, 'Comic Avengers', 'Comic de los Avengers', 14.99, 50, 3, 'https://paninitienda.com/cdn/shop/files/27579_900x900.jpg?v=1710264827');
