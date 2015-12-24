package com.danielvandenbrink.corena.util;

import java.nio.ByteBuffer;

public final class Convert {
    private Convert() {
    }

    public static byte[] shortToByteArray(final short s) {
        final byte[] bytes = new byte[2];
        for (int i = 0; i < 2; ++i) {
            bytes[i] = (byte) (s >> (i * 8));
        }
        return bytes;
    }

    public static byte[] shortToByteArray(final short s, final byte[] bytes, final int offset) {
        assert bytes.length >= 2 + offset;
        for (int i = 0; i < 2; ++i) {
            bytes[i + offset] = (byte) (s >> (i * 8));
        }
        return bytes;
    }

    public static byte[] intToByteArray(final int integer) {
        final byte[] bytes = new byte[4];
        for (int i = 0; i < 4; ++i) {
            bytes[i] = (byte) (integer >> (i * 8));
        }
        return bytes;
    }

    public static byte[] intToByteArray(final int integer, final byte[] bytes, final int offset) {
        assert bytes.length >= 4 + offset;
        for (int i = 0; i < 4; ++i) {
            bytes[i + offset] = (byte) (integer >> (i * 8));
        }
        return bytes;
    }

    public static byte[] longToByteArray(final long l) {
        final byte[] bytes = new byte[8];
        for (int i = 0; i < 8; ++i) {
            bytes[i] = (byte) (l >> (i * 8));
        }
        return bytes;
    }

    public static byte[] longToByteArray(final long l, final byte[] bytes, final int offset) {
        assert bytes.length >= 8 + offset;
        for (int i = 0; i < 8; ++i) {
            bytes[i + offset] = (byte) (l >> (i * 8));
        }
        return bytes;
    }

    public static byte[] floatToByteArray(final float f) {
        return ByteBuffer.allocate(4).putFloat(f).array();
    }

    public static byte[] floatToByteArray(final float f, final byte[] bytes, final int offset) {
        ByteBuffer bb = ByteBuffer.wrap(bytes);
        bb.position(offset);
        bb.putFloat(f);
        return bb.array();
    }

    public static short byteArrayToShort(final byte[] bytes) {
        assert bytes.length >= 2;
        short s = 0;
        for (int i = 0; i < 2; ++i) {
            s |= (bytes[i] & 0xFF) << (i << 3);
        }
        return s;
    }

    public static short byteArrayToShort(final byte[] bytes, final int offset) {
        assert bytes.length >= 2 + offset;
        short s = 0;
        for (int i = 0; i < 2; ++i) {
            s |= (bytes[i + offset] & 0xFF) << (i << 3);
        }
        return s;
    }

    public static int byteArrayToInt(final byte[] bytes) {
        assert bytes.length >= 4;
        int integer = 0;
        for (int i = 0; i < 4; ++i) {
            integer |= (bytes[i] & 0xFF) << (i << 3);
        }
        return integer;
    }

    public static int byteArrayToInt(final byte[] bytes, final int offset) {
        assert bytes.length >= 4 + offset;
        int integer = 0;
        for (int i = 0; i < 4; ++i) {
            integer |= (bytes[i + offset] & 0xFF) << (i << 3);
        }
        return integer;
    }

    public static long byteArrayToLong(final byte[] bytes) {
        assert bytes.length >= 8;
        long l = 0;
        for (int i = 0; i < 8; ++i) {
            l |= (bytes[i] & 0xFF) << (i << 3);
        }
        return l;
    }

    public static long byteArrayToLong(final byte[] bytes, final int offset) {
        assert bytes.length >= 8 + offset;
        long l = 0;
        for (int i = 0; i < 8; ++i) {
            l |= (bytes[i + offset] & 0xFF) << (i << 3);
        }
        return l;
    }

    public static float byteArrayToFloat(final byte[] bytes) {
        return ByteBuffer.wrap(bytes).getFloat();
    }

    public static float byteArrayToFloat(final byte[] bytes, final int offset) {
        ByteBuffer bb = ByteBuffer.wrap(bytes);
        bb.position(offset);
        return bb.getFloat();
    }
}
