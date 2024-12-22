CREATE TABLE UserProfile (
    user_id BIGINT REFERENCES User(id) ON DELETE CASCADE,
    profile_id BIGINT REFERENCES Profile(id) ON DELETE CASCADE,
    PRIMARY KEY (user_id, profile_id)
);