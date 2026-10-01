CREATE TABLE product
(
    id uuid primary key,
    name varchar not null,
    price numeric(10,2) not null,
    stock_quantity int not null
);

CREATE TABLE orders
(
    id uuid primary key,
    customer_email varchar not null,
    total_price numeric(10,2) not null,
    order_date timestamp not null
);

CREATE TABLE order_line
(
    id uuid primary key,
    order_id uuid not null,
    product_id uuid not null,
    quantity int not null,
    unit_price numeric(10,2) not null,
    constraint fk_order_line_orders FOREIGN KEY (order_id) REFERENCES order(id) ON DELETE CASCADE,
    constraint fk_product_line_product FOREIGN KEY(product_id) REFERENCES product(id)
);