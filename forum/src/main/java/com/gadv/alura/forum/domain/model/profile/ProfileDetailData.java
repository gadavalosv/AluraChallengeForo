package com.gadv.alura.forum.domain.model.profile;

public record ProfileDetailData(
        Short id,
        String name
) {
    public ProfileDetailData(Profile profile) {
        this(profile.getId(), profile.getName());
    }
}
