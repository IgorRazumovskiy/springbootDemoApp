DELETE FROM marketplace_order_item;
DELETE FROM marketplace_order;

ALTER SEQUENCE marketplace_order_mo_id_seq RESTART WITH 11;

INSERT INTO marketplace_order (mo_time, mo_currency, mo_customer_full_name, mo_customer_email, mo_order_status) VALUES
('2021-05-31 15:42:44.089000', 'EUR', 'Norbert Jahns', 'norbert.jahns@server.de', 'SUCCESS'),
('2021-05-14 12:39:24.009000', 'USD', 'David Remling', 'david.remling@post.com', 'FAILED'),
('2021-05-31 12:54:24.484000', 'USD', 'Akram Sekkari', 'sekkari@gmail.com', 'PENDING');

INSERT INTO marketplace_order_item (moi_order_id, moi_product_id, moi_product_name, moi_offer_price, moi_count, moi_total_item_cost) VALUES
(11, 7897246, 'Continental ContiContact TS815', 10059, 2, 20118),
(11, 9408704, 'Nexen N blue 4 Season', 4942, 4, 19768),
(12, 7565498, 'Pirelli Cinturato P7 Blue', 7250, 4, 29000),
(13, 7176439, 'Hankook Ventus Prime 3 K125', 4627, 4, 18508),
(13, 1077364, 'Toyo Celsius', 8918, 4, 35672);