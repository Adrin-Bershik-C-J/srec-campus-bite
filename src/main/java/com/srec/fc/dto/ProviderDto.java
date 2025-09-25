package com.srec.fc.dto;

import com.srec.fc.entity.Provider;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ProviderDto {
    private Long id;
    private String providerName;
    private String contact;
    private Long userId; // reference to User

    private ProviderSettingsDto settings;
    private java.util.List<MenuItemDto> menuItems;

    // convenience constructor
    public ProviderDto(Provider provider) {
        this.id = provider.getId();
        this.providerName = provider.getProviderName();
        this.contact = provider.getContact();
        this.userId = provider.getUser() != null ? provider.getUser().getId() : null;
        // settings + menuItems → map later if needed
    }
}
