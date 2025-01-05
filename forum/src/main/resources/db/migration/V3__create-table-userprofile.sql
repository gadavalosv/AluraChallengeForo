CREATE TABLE UserProfile (
    user_id BIGINT REFERENCES users(id) ON DELETE CASCADE,
    profile_id SMALLINT REFERENCES profiles(id) ON DELETE CASCADE,
    PRIMARY KEY (user_id, profile_id)
);