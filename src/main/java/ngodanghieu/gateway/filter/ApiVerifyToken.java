package ngodanghieu.gateway.filter;

import lombok.SneakyThrows;
import org.jose4j.jwt.JwtClaims;
import org.jose4j.jwt.consumer.JwtConsumer;
import org.jose4j.jwt.consumer.JwtConsumerBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import sun.misc.BASE64Decoder;

import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.spec.X509EncodedKeySpec;
import java.util.Map;


public class ApiVerifyToken {

    @Value("${app.public-key-file}")
    private String public_key = "-----BEGIN PUBLIC KEY-----\n" +
            "MIICIjANBgkqhkiG9w0BAQEFAAOCAg8AMIICCgKCAgEA6T7uDn22SVA2VgVpxQrb\n" +
            "ywRC0iOSxXr53MAwtZqElhJIQsgBwB2tuiPgXdiaI7iC63+HbmK/X9CCSY/dNdGH\n" +
            "P/agW0/FoGqa44uyMrvGakUQQB9Ur5qToIQvdYfiQ9sPwg38KZFQp2HCPldYuOwj\n" +
            "xGXv6PlzjfavymeJ/YswJ6QD1ajuyidIz7J6wwgY/hnFRWXikPRrhoWQJqa7rko0\n" +
            "+4MnMG2NpvkdBvAVKvUk3qdqpu0svtHeTovVkvynpbL38xurhM1LyWGm/AqAeHyD\n" +
            "YK1+pdKm75Y3eNnTOgnfWLWBSVN7kT/r0VRvIdR68Gui8uyYbrEW1KxBQvkXWGiX\n" +
            "r7G8e42ilucV032Es6vi3tEb2XSG5AElqZ9i0zUP3ehChhXBYWqAdpf4NA5wREBF\n" +
            "H5ZHWE2SMmFgJlN3hYSGUScZMzUVIShMUHt67bitDmO3LtYi8INMtU7K7BU5zqn2\n" +
            "siaylBuEjy6VcNFbN92zxFPDaqu0HVNSCqtGpj1G0vxd/I6kclaetJdPYsENK373\n" +
            "Y8RMHWrra+TWcgaFY6G/rWV7FjR8bW8AD9YyUPv8NstcpJPJnqAhJGNP0HeciF31\n" +
            "ZakTzluhi9Z8APrNhyPMQaNsHzlWs6q4NmanYhU0rj5mXFL3sqcgaO4Z4izovi4S\n" +
            "Iw3lJ+hHFUpWn3gRta7mRUcCAwEAAQ==\n" +
            "-----END PUBLIC KEY-----";
    private String token;

    public ApiVerifyToken(String token) {
        this.token = token;
    }

    @SneakyThrows
    public Map<String, Object> verifyToken() {
        if (token == null || !token.startsWith(FilterConstant.PREFIX)) return null;


        public_key = public_key.replace("-----BEGIN PUBLIC KEY-----\n", "");
        public_key = public_key.replace("-----END PUBLIC KEY-----", "");
        BASE64Decoder base64Decoder = new BASE64Decoder();
        byte[] publicKeyBytes = base64Decoder.decodeBuffer(public_key);

        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(publicKeyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        PublicKey publicKey = keyFactory.generatePublic(keySpec);

        JwtConsumer jwtConsumer = new JwtConsumerBuilder()
                .setRequireExpirationTime()
                .setVerificationKey(publicKey)
                .build();

        try {
            JwtClaims jwtDecoded = jwtConsumer.processToClaims(token.replace(FilterConstant.PREFIX, ""));
            Map<String, Object> jwtClaims = jwtDecoded.getClaimsMap();
            Map<String, Object> object_permissions = (Map<String, Object>) jwtClaims.get("permissions");
            if (checkVinPayCashbackManagement(object_permissions))
                return  jwtClaims;
            else
                return null;
        }catch (Exception e){

            return null;
        }

    }

    private Boolean checkVinPayCashbackManagement(Map< String,Object> object_permissions){
        return object_permissions.get("VinPay_CashbackManagement") != null;
    }
}
