CREATE TABLE IF NOT EXISTS spring_ai_chat_history (
    id VARCHAR(255) PRIMARY KEY,
    conversation_id VARCHAR(255) NOT NULL,
    content TEXT NOT NULL,
    type VARCHAR(50) NOT NULL CHECK (type IN ('USER', 'ASSISTANT', 'SYSTEM', 'TOOL')),
    timestamp TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL
    );

CREATE INDEX IF NOT EXISTS spring_ai_chat_history_index
    ON spring_ai_chat_history(conversation_id);

ALTER TABLE spring_ai_chat_history
ALTER COLUMN id SET DEFAULT gen_random_uuid()::varchar;