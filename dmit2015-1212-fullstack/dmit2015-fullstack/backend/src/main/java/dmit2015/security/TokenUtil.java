/**
 * To use this tool you first need to generate the public/private key pair for signing and verifying JWT tokens.
 * Open a Terminal session in project folder and type the following commands:

 openssl genpkey -algorithm RSA -out ~/keys/jwt-private.pem -pkeyopt rsa_keygen_bits:2048

 openssl rsa -in ~/keys/jwt-private.pem -pubout -out src/main/resources/META-INF/jwt-public.pem

 *
 * You can now use this utility to generate JWT tokens

 mvn exec:java -Dexec.mainClass=common.security.TokenUtil -Dexec.classpathScope=test -Dexec.args="/home/user2015/keys/jwt-private.pem user2015 USER"

 mvn exec:java -Dexec.mainClass=common.security.TokenUtil -Dexec.classpathScope=test -Dexec.args="/home/user2015/keys/jwt-private.pem admin2105 ADMIN"

 mvn exec:java -Dexec.mainClass=common.security.TokenUtil -Dexec.classpathScope=test -Dexec.args="/home/user2015/keys/jwt-private.pem useradmin2015 USER ADMIN"

 *
 */
package dmit2015.security;

import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.RSASSASigner;

import jakarta.json.Json;
import jakarta.json.JsonArrayBuilder;
import jakarta.json.JsonObjectBuilder;
import java.io.FileReader;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Base64;
import java.util.UUID;

public class TokenUtil {

    private static PrivateKey loadPrivateKey(final String privateKeyFilePath) throws Exception {
        Path privateKeyFile = Paths.get(privateKeyFilePath);
        try (FileReader fileReader = new FileReader(privateKeyFile.toFile())) {

            char[] contents = new char[4096];
            int length = fileReader.read(contents);

            String rawKey = new String(contents, 0, length)
                    .replaceAll("-----BEGIN (.*)-----", "")
                    .replaceAll("-----END (.*)----", "")
                    .replaceAll("\r\n", "").replaceAll("\n", "")
                    .trim();

            PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(Base64.getDecoder().decode(rawKey));
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");

            return keyFactory.generatePrivate(keySpec);
        }
    }

    public static String generateJWT(final String privateKeyFilePath, final String principal, final String... groups) throws Exception {
        PrivateKey privateKey = loadPrivateKey(privateKeyFilePath);

        JWSSigner signer = new RSASSASigner(privateKey);
        JsonArrayBuilder groupsBuilder = Json.createArrayBuilder();
        for (String group : groups) {
            groupsBuilder.add(group);
        }

        long currentTime = System.currentTimeMillis() / 1000;	// Convert time from milliseconds to seconds
        JsonObjectBuilder claimsBuilder = Json.createObjectBuilder()
                .add("sub", principal)
                .add("upn", principal)
                .add("iss", "quickstart-jwt-issuer")
                .add("aud", "jwt-audience")
                .add("groups", groupsBuilder.build())
                .add("jti", UUID.randomUUID().toString())
                .add("iat", currentTime)
                .add("exp", currentTime + 14400);	// Set expiry time to 4 hours (4 * 60 * 60)

        JWSObject jwsObject = new JWSObject(
                new JWSHeader.Builder(JWSAlgorithm.RS256).type(new JOSEObjectType("jwt")).keyID("quickstart-jwt-issuer").build(),
                new Payload(claimsBuilder.build().toString()));

        jwsObject.sign(signer);

        return jwsObject.serialize();
    }

    public static void main(String[] args) throws Exception {
        if (args.length < 2)
            throw new IllegalArgumentException("Usage TokenUtil {privateKeyFile} {principal} {groups}");
        String privateKeyFile = args[0];
        String principal = args[1];
        String[] groups = new String[args.length - 1];
        System.arraycopy(args, 1, groups, 0, groups.length);

        String token = generateJWT(privateKeyFile, principal, groups);
        String[] parts = token.split("\\.");
        System.out.println(String.format("\nJWT Header - %s", new String(Base64.getDecoder().decode(parts[0]), StandardCharsets.UTF_8)));
        System.out.println(String.format("\nJWT Claims - %s", new String(Base64.getDecoder().decode(parts[1]), StandardCharsets.UTF_8)));
        System.out.println(String.format("\nGenerated JWT Token \n%s\n", token));
    }
}