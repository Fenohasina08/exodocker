
CREATE TABLE flyway_schema_history
(
    installed_rank INTEGER                                   NOT NULL,
    version        VARCHAR(50),
    description    VARCHAR(200)                              NOT NULL,
    type           VARCHAR(20)                               NOT NULL,
    script         VARCHAR(1000)                             NOT NULL,
    checksum       INTEGER,
    installed_by   VARCHAR(100)                              NOT NULL,
    installed_on   TIMESTAMP WITHOUT TIME ZONE DEFAULT NOW() NOT NULL,
    execution_time INTEGER                                   NOT NULL,
    success        BOOLEAN                                   NOT NULL,
    CONSTRAINT flyway_schema_history_pk PRIMARY KEY (installed_rank)
);

CREATE TABLE todo
(
    id           VARCHAR                     DEFAULT (uuid_generate_v4()) NOT NULL,
    title        VARCHAR                                                  NOT NULL,
    description  TEXT,
    is_completed BOOLEAN                     DEFAULT FALSE,
    created_at   TIMESTAMP WITHOUT TIME ZONE DEFAULT NOW(),
    updated_at   TIMESTAMP WITHOUT TIME ZONE DEFAULT NOW(),
    CONSTRAINT todo_pkey PRIMARY KEY (id)
);

CREATE INDEX flyway_schema_history_s_idx ON flyway_schema_history (success);

INSERT INTO todo (
    title,
    description,
    is_completed,
    created_at,
    updated_at
) VALUES
      ('Learn Spring Boot', 'Complete a full Spring Boot tutorial.', FALSE, NOW(), NOW()),
      ('Build REST API', 'Develop a CRUD REST API for task management.', TRUE, NOW(), NOW()),
      ('Buy groceries', 'Purchase milk, eggs, bread, and vegetables.', FALSE, NOW(), NOW()),
      ('Review Java', 'Practice object-oriented programming and collections.', TRUE, NOW(), NOW()),
      ('Update resume', 'Add recent projects and skills to the resume.', FALSE, NOW(), NOW()),
      ('Apply for internship', 'Submit an application to a software company.', TRUE, NOW(), NOW()),
      ('Read a book', 'Read 30 pages of a software engineering book.', FALSE, NOW(), NOW()),
      ('Exercise', 'Go for a 45-minute run.', FALSE, NOW(), NOW()),
      ('Clean workspace', 'Organize the desk and remove unnecessary files.', TRUE, NOW(), NOW()),
      ('Learn Docker', 'Containerize a Spring Boot application.', FALSE, NOW(), NOW()),
      ('Fix bugs', 'Resolve issues reported during testing.', TRUE, NOW(), NOW()),
      ('Prepare presentation', 'Create slides for the university project.', FALSE, NOW(), NOW()),
      ('Push code to GitHub', 'Commit and push the latest changes.', TRUE, NOW(), NOW()),
      ('Practice SQL', 'Work on JOIN, GROUP BY, and indexing exercises.', FALSE, NOW(), NOW()),
      ('Watch tech talk', 'Watch a conference about microservices.', FALSE, NOW(), NOW()),
      ('Backup files', 'Create backups of important project files.', TRUE, NOW(), NOW()),
      ('Study React', 'Build a small React and TypeScript application.', FALSE, NOW(), NOW()),
      ('Install PostgreSQL', 'Set up PostgreSQL on a new environment.', TRUE, NOW(), NOW()),
      ('Test API endpoints', 'Verify all endpoints using Postman.', FALSE, NOW(), NOW()),
      ('Write documentation', 'Document the REST API endpoints.', FALSE, NOW(), NOW());


select * from todo;