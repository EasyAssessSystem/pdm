#system users
create table users (
    id bigint auto_increment NOT NULL,
    username varchar(30) not null UNIQUE,
    password VARCHAR(60) not NULL,
    name VARCHAR(30) not NULL,
    can_launch_assessment boolean default false,
    status VARCHAR (1) NOT NULL DEFAULT 'A',
    PRIMARY KEY(id)
);

#user roles
create table roles (
    id bigint auto_increment NOT NULL,
    name varchar(30) not null UNIQUE,
    status VARCHAR (1) NOT NULL DEFAULT  'A',
    PRIMARY KEY(id)
);

#users roles map
create table users_roles (
    id bigint auto_increment NOT NULL,
    user_id bigint not null,
    role_id bigint not null,
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE ON UPDATE NO ACTION,
    FOREIGN KEY (role_id) REFERENCES roles(id) ON DELETE CASCADE ON UPDATE NO ACTION,
    PRIMARY KEY(id)
);

#health ministries
create table health_ministries (
    id bigint auto_increment NOT NULL,
    name varchar(60) not null UNIQUE,
    supervisor_id bigint,
    type VARCHAR(1) NOT NULL DEFAULT 'C',
    status VARCHAR (1) NOT NULL DEFAULT  'A',
    FOREIGN KEY (supervisor_id) REFERENCES health_ministries(id) ON DELETE SET NULL ON UPDATE NO ACTION,
    PRIMARY KEY(id)
);

#users roles map
create table users_ministries (
    id bigint auto_increment NOT NULL,
    user_id bigint not null,
    ministry_id bigint not null,
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE ON UPDATE NO ACTION,
    FOREIGN KEY (ministry_id) REFERENCES health_ministries(id) ON DELETE CASCADE ON UPDATE NO ACTION,
    PRIMARY KEY(id)
);

#assay categories
create table assay_categories (
    id bigint auto_increment NOT NULL,
    name varchar(60) not null UNIQUE,
    status VARCHAR (1) NOT NULL DEFAULT  'A',
    PRIMARY KEY(id)
);

#code groups
create table assay_code_groups (
    id bigint auto_increment NOT NULL,
    name varchar(60) not null UNIQUE,
    status VARCHAR (1) NOT NULL DEFAULT  'A',
    PRIMARY KEY(id)
);

#codes
create table assay_codes (
    id bigint auto_increment NOT NULL,
    code_number varchar(10) not null,
    name varchar(60) not null,
    group_id bigint,
    status VARCHAR (1) NOT NULL DEFAULT  'A',
    FOREIGN KEY (group_id) REFERENCES assay_code_groups(id) ON DELETE SET NULL ON UPDATE NO ACTION,
    PRIMARY KEY(id)
);

# codes and categories map
create table assay_codes_categories(
    id bigint auto_increment NOT NULL,
    code_id bigint not null,
    category_id bigint not null,
    FOREIGN KEY (code_id) REFERENCES assay_codes(id) ON DELETE CASCADE ON UPDATE NO ACTION,
    FOREIGN KEY (category_id) REFERENCES assay_categories(id) ON DELETE CASCADE ON UPDATE NO ACTION,
    PRIMARY KEY(id)
);


INSERT INTO users VALUES (1, 'esadmin','8forxiao','System Admin', true, 'A');
INSERT INTO users VALUES (2, 'esuser','8forxiao','System User', false, 'A');

INSERT INTO roles VALUES (1, '系统管理员', 'A');
INSERT INTO roles VALUES (2, '系统用户', 'A');

INSERT INTO users_roles VALUES (1, 1, 1);
INSERT INTO users_roles VALUES (2, 2, 2);

INSERT INTO health_ministries VALUES (1, '中国CDC总局',NULL ,'C', 'A');
INSERT INTO users_ministries VALUES (1, 2, 1);

INSERT INTO assay_categories VALUES (1, '血型检测', 'A');
INSERT INTO assay_categories VALUES (2, '尿常规', 'A');
INSERT INTO assay_categories VALUES (3, '肝功检测', 'A');

INSERT INTO assay_code_groups VALUES (1, '仪器', 'A');
INSERT INTO assay_code_groups VALUES (2, '试剂', 'A');
INSERT INTO assay_code_groups VALUES (3, '方法', 'A');

INSERT INTO assay_codes VALUES (1, '100001', '血型速测试纸', 2, 'A');
INSERT INTO assay_codes VALUES (2, '200001', '干血斑法', 3, 'A');
INSERT INTO assay_codes VALUES (3, '300001', '尿液分离器', 1, 'A');




