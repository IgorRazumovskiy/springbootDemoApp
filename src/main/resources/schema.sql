CREATE SEQUENCE marketplace_order_mo_id_seq;
CREATE SEQUENCE marketplace_order_item_moi_id_seq;


CREATE TABLE marketplace_order
(
    mo_id                   BIGINT DEFAULT NEXT VALUE FOR marketplace_order_mo_id_seq PRIMARY KEY,
    mo_time                 TIMESTAMP,
    mo_currency             TEXT,
    mo_customer_full_name   TEXT,
    mo_customer_email       TEXT,
    mo_order_status         TEXT
);

CREATE TABLE marketplace_order_item
(
    moi_id                      BIGINT DEFAULT NEXT VALUE FOR marketplace_order_item_moi_id_seq PRIMARY KEY,
    moi_order_id                INTEGER,
    moi_product_id              INTEGER,
    moi_product_name            TEXT,
    moi_offer_price             INTEGER,
    moi_count                   INTEGER,
    moi_total_item_cost         INTEGER
);

ALTER TABLE marketplace_order_item
    ADD FOREIGN KEY (moi_order_id) REFERENCES marketplace_order (mo_id);