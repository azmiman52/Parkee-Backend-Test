create sequence book_seq start with 1 increment by 1;
create sequence rent_history_seq start with 1 increment by 1;
create sequence users_seq start with 1 increment by 1;
create table book
(
    id    integer not null,
    stock integer,
    isbn  varchar(255) unique,
    title varchar(255) unique,
    primary key (id)
);
comment
on column book.stock is 'Stock of The Book';
comment
on column book.isbn is 'ISBN';
comment
on column book.title is 'Title of The Book';
create table users
(
    can_borrow boolean default true,
    id    integer not null,
    email varchar(255),
    name  varchar(255),
    nik   varchar(255) unique,
    primary key (id)
);
comment
on column users.can_borrow is 'Check if the user can borrow a book or not';
comment
on column users.email is 'User''s email';
comment on column users.name is 'Name';
comment on column users.nik is 'User''s NIK';
create table rent_history
(
    book_id     integer,
    id          integer not null,
    rent_date   date,
    rent_due    date,
    return_date date,
    user_id     integer,
    status      varchar(255) check (status in ('ON_TIME', 'OVERDUE', 'BORROWED')),
    primary key (id),
    foreign key (user_id) references users (id),
    foreign key (book_id) references book (id)
);
comment
on column rent_history.rent_date is 'Date when the rent started';
comment
on column rent_history.rent_due is 'Due day of the rent';
comment
on column rent_history.return_date is 'Return date of the book';
comment
on column rent_history.status is 'Enum of ReturnStatus';
