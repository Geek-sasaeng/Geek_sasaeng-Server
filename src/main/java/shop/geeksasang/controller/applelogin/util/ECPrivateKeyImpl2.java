//package shop.geeksasang.controller.applelogin.util;
//
//import sun.security.pkcs.PKCS8Key;
//import sun.security.util.*;
//import sun.security.x509.AlgorithmId;
//
//import java.io.IOException;
//import java.math.BigInteger;
//import java.security.AlgorithmParameters;
//import java.security.InvalidKeyException;
//import java.security.interfaces.ECPrivateKey;
//import java.security.spec.ECParameterSpec;
//import java.security.spec.InvalidParameterSpecException;
//import java.util.Arrays;
//
///**
// * Key implementation for EC private keys.
// *
// * ASN.1 syntax for EC private keys from SEC 1 v1.5 (draft):
// *
// * <pre>
// * EXPLICIT TAGS
// *
// * ECPrivateKey ::= SEQUENCE {
// *   version INTEGER { ecPrivkeyVer1(1) } (ecPrivkeyVer1),
// *   privateKey OCTET STRING,
// *   parameters [0] ECDomainParameters {{ SECGCurveNames }} OPTIONAL,
// *   publicKey [1] BIT STRING OPTIONAL
// * }
// * </pre>
// *
// * We currently ignore the optional parameters and publicKey fields. We
// * require that the parameters are encoded as part of the AlgorithmIdentifier,
// * not in the private key structure.
// *
// * @since   1.6
// * @author  Andreas Sterbenz
// */
//public final class ECPrivateKeyImpl2 extends PKCS8Key implements ECPrivateKey {
//
//    private static final long serialVersionUID = 88695385615075129L;
//
//    private BigInteger s;       // private value
//    private byte[] arrayS;      // private value as a little-endian array
//    private ECParameterSpec params;
//
//    /**
//     * Construct a key from its encoding. Called by the ECKeyFactory.
//     */
//    ECPrivateKeyImpl2(byte[] encoded) throws InvalidKeyException {
//        super(encoded);
//        parseKeyBits();
//    }
//
//    /**
//     * Construct a key from its components. Used by the
//     * KeyFactory.
//     */
//    ECPrivateKeyImpl2(BigInteger s, ECParameterSpec params)
//            throws InvalidKeyException {
//        this.s = s;
//        this.params = params;
//        makeEncoding(s);
//
//    }
//
//    ECPrivateKeyImpl2(byte[] s, ECParameterSpec params)
//            throws InvalidKeyException {
//        this.arrayS = s.clone();
//        this.params = params;
//        makeEncoding(s);
//    }
//
//    private void makeEncoding(byte[] s) throws InvalidKeyException {
//        algid = new AlgorithmId
//                (AlgorithmId.EC_oid, ECParameters.getAlgorithmParameters(params));
//        try {
//            DerOutputStream out = new DerOutputStream();
//            out.putInteger(1); // version 1
//            byte[] privBytes = s.clone();
//            ArrayUtil.reverse(privBytes);
//            out.putOctetString(privBytes);
//            Arrays.fill(privBytes, (byte)0);
//            DerValue val = DerValue.wrap(DerValue.tag_Sequence, out);
//            key = val.toByteArray();
//            val.clear();
//        } catch (IOException exc) {
//            // should never occur
//            throw new InvalidKeyException(exc);
//        }
//    }
//
//    private void makeEncoding(BigInteger s) throws InvalidKeyException {
//        algid = new AlgorithmId(AlgorithmId.EC_oid,
//                ECParameters.getAlgorithmParameters(params));
//        try {
//            byte[] sArr = s.toByteArray();
//            // convert to fixed-length array
//            int numOctets = (params.getOrder().bitLength() + 7) / 8;
//            byte[] sOctets = new byte[numOctets];
//            int inPos = Math.max(sArr.length - sOctets.length, 0);
//            int outPos = Math.max(sOctets.length - sArr.length, 0);
//            int length = Math.min(sArr.length, sOctets.length);
//            System.arraycopy(sArr, inPos, sOctets, outPos, length);
//            Arrays.fill(sArr, (byte)0);
//
//            DerOutputStream out = new DerOutputStream();
//            out.putInteger(1); // version 1
//            out.putOctetString(sOctets);
//            Arrays.fill(sOctets, (byte)0);
//            DerValue val = DerValue.wrap(DerValue.tag_Sequence, out);
//            key = val.toByteArray();
//            val.clear();
//        } catch (IOException exc) {
//            throw new AssertionError("Should not happen", exc);
//        }
//    }
//
//    // see JCA doc
//    public String getAlgorithm() {
//        return "EC";
//    }
//
//    // see JCA doc
//    public BigInteger getS() {
//        if (s == null) {
//            byte[] arrCopy = arrayS.clone();
//            ArrayUtil.reverse(arrCopy);
//            s = new BigInteger(1, arrCopy);
//            Arrays.fill(arrCopy, (byte)0);
//        }
//        return s;
//    }
//
//    public byte[] getArrayS() {
//        if (arrayS == null) {
//            arrayS = ECUtil.sArray(getS(), params);
//        }
//        return arrayS.clone();
//    }
//
//    // see JCA doc
//    public ECParameterSpec getParams() {
//        return params;
//    }
//
//    private void parseKeyBits() throws InvalidKeyException {
//        try {
//            DerInputStream in = new DerInputStream(key);
//            DerValue derValue = in.getDerValue();
//            if (derValue.tag != DerValue.tag_Sequence) {
//                throw new IOException("Not a SEQUENCE");
//            }
//            DerInputStream data = derValue.data;
//            int version = data.getInteger();
//            if (version != 1) {
//                throw new IOException("Version must be 1");
//            }
//            byte[] privData = data.getOctetString();
//            ArrayUtil.reverse(privData);
//            arrayS = privData;
//            while (data.available() != 0) {
//                DerValue value = data.getDerValue();
//                if (value.isContextSpecific((byte) 0)) {
//                    // ignore for now
//                } else if (value.isContextSpecific((byte) 1)) {
//                    // ignore for now
//                } else {
//                    throw new InvalidKeyException("Unexpected value: " + value);
//                }
//            }
//            AlgorithmParameters algParams = this.algid.getParameters();
//            if (algParams == null) {
//                throw new InvalidKeyException("EC domain parameters must be "
//                        + "encoded in the algorithm identifier");
//            }
//            params = algParams.getParameterSpec(ECParameterSpec.class);
//        } catch (IOException e) {
//            throw new InvalidKeyException("Invalid EC private key", e);
//        } catch (InvalidParameterSpecException e) {
//            throw new InvalidKeyException("Invalid EC private key", e);
//        }
//    }
//}
//
