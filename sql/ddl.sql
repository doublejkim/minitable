create table user(
                         user_id     bigint not null auto_increment,
                         authority      varchar(20) not null,
                         email           varchar(50) not null,
                         password        varchar(100) not null,
                         username   varchar(50) not null,
                         twitter_id      varchar(50),
                         gender          varchar(10) not null,
                         age             int not null,
                         enabled         varchar(1) not null default 'Y',
                         created_at      timestamp,
                         modified_at     timestamp,
                         PRIMARY KEY pk_customer (user_Id),
                         UNIQUE KEY uk_customer_01 (email)
);

create table store (
                       store_id            bigint not null auto_increment,
                       user_id             bigint not null,
                       store_name          varchar(50) not null,
                       address             varchar(100) not null,
                       phone               varchar(50) not null,
                       start_time          timestamp not null,
                       end_time            timestamp not null,
                       off_day             varchar(50),
                       dtype               varchar(50) not null,
                       booking_limit_yn    varchar(1) not null,
                       star_avg        double,
                       created_at      timestamp,
                       modified_at     timestamp,
                       PRIMARY KEY pk_store (store_id),
                       FOREIGN KEY fk_store_01 (user_id) REFERENCES user (user_id),
                       UNIQUE KEY uk_store_01 (store_name)
);

create table restaurant (
                            restaurant_id       bigint not null auto_increment,
                            store_id            bigint not null,
                            start_breaktime     timestamp,
                            end_breaktime       timestamp,
                            pest_control_yn     varchar(1) not null,
                            last_pest_control_date  timestamp,
                            created_at      timestamp,
                            modified_at     timestamp,
                            PRIMARY KEY pk_restaurant (restaurant_id),
                            FOREIGN KEY fk_restaurant_01 (store_id) REFERENCES store (store_id)
);

create table menu (
                      menu_id         bigint not null auto_increment,
                      restaurant_id   bigint not null,
                      menu_name       varchar(50) not null,
                      menu_desc       varchar(200),
                      menu_image      blob,
                      created_at      timestamp,
                      modified_at     timestamp,
                      PRIMARY KEY pk_menu (menu_id),
                      FOREIGN KEY fk_menu_01 (restaurant_id) REFERENCES restaurant (restaurant_id)
);

create table booking (
                         booking_id          bigint not null auto_increment,
                         user_Id         bigint not null,
                         store_id            bigint not null,
                         criterion_date      varchar(8) not null,
                         complete_yn         varchar(1) not null,
                         forced_canceled_yn  varchar(1) not null,
                         call_count          int not null,
                         created_at          timestamp,
                         modified_at         timestamp,
                         PRIMARY KEY pk_booking (booking_id),
                         FOREIGN KEY fk_booking_01 (user_Id) REFERENCES user (user_Id),
                         FOREIGN KEY fk_booking_02 (store_id) REFERENCES store (store_id),
                         UNIQUE KEY uk_booking_01 (user_Id, store_id, criterion_date)
);

create table review (
                        review_id       bigint not null auto_increment,
                        user_Id     bigint not null,
                        store_id        bigint not null,
                        booking_at      timestamp not null,
                        star            int not null,
                        review_text     varchar(200),
                        review_image    blob,
                        created_at      timestamp,
                        modified_at     timestamp,
                        PRIMARY KEY pk_review (review_id),
                        FOREIGN KEY fk_review_01 (user_Id) REFERENCES user (user_Id),
                        FOREIGN KEY fk_review_02 (store_id) REFERENCES store (store_id)
);

create table store_convenience (
                                   sc_id           bigint not null auto_increment,
                                   store_id        bigint not null,
                                   convenience_id  bigint not null,
                                   PRIMARY KEY pk_store_convenience (sc_id)
);

create table convenience (
                             convenience_id      bigint not null auto_increment,
                             name    varchar(50) not null,
                             count   int not null,
                             PRIMARY KEY pk_convenience (convenience_id)
);



