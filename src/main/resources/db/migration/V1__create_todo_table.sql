CREATE EXTENSION IF NOT EXISTS "uuid-ossp";


CREATE TABLE IF NOT EXISTS todo (

                                    id VARCHAR PRIMARY KEY DEFAULT uuid_generate_v4()::text,

    title VARCHAR NOT NULL,

    description TEXT,

    is_completed BOOLEAN DEFAULT FALSE,

    created_at TIMESTAMPTZ DEFAULT NOW(),

    updated_at TIMESTAMPTZ DEFAULT NOW()

    );