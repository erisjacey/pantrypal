package com.pantrypal.grocerytracker.config.properties;

import com.pantrypal.grocerytracker.constants.Constants;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

@ConfigurationProperties(prefix = Constants.CONFIG_PREFIX_RSA)
public record RsaKeyConfigProperties(RSAPublicKey publicKey, RSAPrivateKey privateKey) {
}
