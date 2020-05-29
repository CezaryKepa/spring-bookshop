insert into book(id, isbn, name,author,book_category, price, pages, description,stock, img_url) VALUES
    (1,	'978-3-16-148410-0','Ekstradycja','Remigiusz Mróz','HORROR',25.99,511,
    'Minęło kilka miesięcy, od kiedy Joanna Chyłka uciekła z kraju. Ślad po niej zaginął i mimo że wystawiono za nią Europejski Nakaz Aresztowania, służby nie potrafią odnaleźć żadnego tropu. ',
    100,'/img/ekstradycja.jpg'),
    (2,	'481-8-34-116410-0','Dylemat','B. A. Paris','MEMOIR',27.41,352,
    'Dylemat to powieść nosząca w sobie znamiona thrillera z pogłębioną psychologią bohaterów.  Livia to dojrzała kobieta, od lat mająca u boku męża, Adama, z którym wbrew rodzicom związała się jeszcze jako nastolatka.',
    100, '/img/dylemat.jpg'),
    (3,	'778-3-16-14510-0','Wiedźmin. Tom 1. Ostatnie życzenie','Andrzej Sapkowski','HORROR',27.49,332,
    'Geralt to wiedźmin, czyli ktoś, kto trudni się mordowaniem różnych bestii za pieniądze. Co ciekawe, tak naprawdę trudno byłoby go nazwać zwyczajnym człowiekiem, tak samo, jak nigdy nie jest i nigdy nie będzie przeciętnym wiedźminem.',
    50, '/img/wiedzmin1.jpg'),
    (4,	'122-3-16-98710-0','Wiedźmin. Tom 2. Miecz przeznaczenia','Andrzej Sapkowski','HORROR',31.68,400,
    'Drugi z kolei tom popularnej serii o Wiedźminie, pogromcy potworów, który nie cofnie się przed niczym. Jaka przygoda spotka go tym razem? Geralt nie ma łatwego życia, mimo że jest doskonale wyćwiczony w tym, co robi i posiada niezaprzeczalny talent',
    75, '/img/wiedzmin2.jpg'),
    (5,	'455-9-22-59870-0','Wiedźmin. Tom 3. Krew elfów','Andrzej Sapkowski','HORROR',31.70,339,
    '"Krew elfów" to pierwsza z pięciu części sagi o Wiedźminie Geralcie. Król polskiej fantastyki, Andrzej Sapkowski, raczy czytelników wspaniałą i wciągającą literaturą na najwyższym poziomie.',
    50, '/img/wiedzmin3.jpg'),
    (6,	'222-1-88-542310-0','Makbet','William Szekspir','MYSTERY',5.78,144,
    'Wydanie Makbeta kompletne bez skrótów i cięć w treści. W tym wydaniu znajdziesz odpowiedzi na pytania z podręcznika - „pewniak na teście”, czyli wskazanie zagadnień, które zwykle pojawiają się w pytaniach z danej lektury we wszelkich testach sprawdzających wiedzę, a także w podręcznikach i na klasówkach.',
    100, '/img/makbet.jpg');
    

insert into user(id, firstname, lastname, email, password) VALUES
    (1,'Jan','Kowalski', 'admin','{bcrypt}$2a$10$0ccVt5R4P3SDbd0NeHYZb.MMilIfFwbnayBA62Cp1cvQM5Qa8KWpG'),
    (2,'Dariusz','Nowak', 'daro@gmail.com','{bcrypt}$2a$10$MEbXa1J5uNOQBZngTJ4RS.lSn6oJCzSTRSiBODhDetkeFW4WisDLa'),
    (3,'Michał','Janikowski', 'janik22@gmail.com','{bcrypt}$2a$10$MEbXa1J5uNOQBZngTJ4RS.lSn6oJCzSTRSiBODhDetkeFW4WisDLa'),
    (4,'Piotr','Zebrzydowski', 'pioteroo@gmail.com','{bcrypt}$2a$10$MEbXa1J5uNOQBZngTJ4RS.lSn6oJCzSTRSiBODhDetkeFW4WisDLa'),
    (5,'Jan','Nowy', 'new@gmail.com','{bcrypt}$2a$10$MEbXa1J5uNOQBZngTJ4RS.lSn6oJCzSTRSiBODhDetkeFW4WisDLa'),
    (6,'Julia','Dobrowolska', 'user','{bcrypt}$2a$10$lKtkZm2kNUkSOfgX7NukmulEbSkEoeZTAnadrPeZPpLmQapG6wupi');

insert into user_role(role, description) VALUES ('ROLE_USER', 'default role for user');
insert into user_role(role, description) VALUES ('ROLE_ADMIN', 'admin role');
insert into user_roles(user_id, roles_id) VALUES ('1', '2');
insert into user_roles(user_id, roles_id) VALUES ('6', '1');