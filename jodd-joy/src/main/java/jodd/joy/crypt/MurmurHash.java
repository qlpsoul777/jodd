// Copyright (c) 2003-present, Jodd Team (http://jodd.org)
// All rights reserved.
//
// Redistribution and use in source and binary forms, with or without
// modification, are permitted provided that the following conditions are met:
//
// 1. Redistributions of source code must retain the above copyright notice,
// this list of conditions and the following disclaimer.
//
// 2. Redistributions in binary form must reproduce the above copyright
// notice, this list of conditions and the following disclaimer in the
// documentation and/or other materials provided with the distribution.
//
// THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
// AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
// IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
// ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE
// LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
// CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
// SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
// INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
// CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
// ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
// POSSIBILITY OF SUCH DAMAGE.

package jodd.joy.crypt;

/**
 * Fast Murmur hash. Strings are hashed faster than in usual
 * implementation, as here we use chars and not bytes.
 */
public class MurmurHash {

	public static long hash64(final byte[] data) {
		final int seed = 0xe17a1465;
		final long m = 0xc6a4a7935bd1e995L;
		final int r = 47;
		final int length = data.length;

		long h = (seed & 0xffffffffL) ^ (length * m);

		int len8 = length >> 3;

		for (int i = 0; i < len8; i++) {
			final int i8 = i << 3;

			long k =
					((long) data[i8] & 0xff) +
							(((long) data[i8 + 1] & 0xff) << 8) +
							(((long) data[i8 + 2] & 0xff) << 16) +
							(((long) data[i8 + 3] & 0xff) << 24) +
							(((long) data[i8 + 4] & 0xff) << 32) +
							(((long) data[i8 + 5] & 0xff) << 40) +
							(((long) data[i8 + 6] & 0xff) << 48) +
							(((long) data[i8 + 7] & 0xff) << 56);

			k *= m;
			k ^= k >>> r;
			k *= m;

			h ^= k;
			h *= m;
		}

		int len7 = length & ~7;
		int left = length - len7;
		switch (left) {
			case 7:
				h ^= (long) (data[len7 + 6] & 0xff) << 48;
			case 6:
				h ^= (long) (data[len7 + 5] & 0xff) << 40;
			case 5:
				h ^= (long) (data[len7 + 4] & 0xff) << 32;
			case 4:
				h ^= (long) (data[len7 + 3] & 0xff) << 24;
			case 3:
				h ^= (long) (data[len7 + 2] & 0xff) << 16;
			case 2:
				h ^= (long) (data[len7 + 1] & 0xff) << 8;
			case 1:
				h ^= (long) (data[len7] & 0xff);
				h *= m;
		}

		h ^= h >>> r;
		h *= m;
		h ^= h >>> r;

		return h;
	}

	public static long hash64(final String string) {
		final int length = string.length();
		final long seed = 0xe17a1465;
		final long m = 0xc6a4a7935bd1e995L;
		final int r = 47;

		long h = (seed & 0xffffffffL) ^ (length * m);

		final int len4 = length >> 2;

		for (int i = 0; i < len4; i++) {
			final int i8 = i << 2;

			long k =
					((long) string.charAt(i8) & 0xFFFF) |
					(((long) string.charAt(i8 + 1) & 0xFFFF) << 16) +
					(((long) string.charAt(i8 + 2) & 0xFFFF) << 32) +
					(((long) string.charAt(i8 + 3) & 0xFFFF) << 48);

			k *= m;
			k ^= k >>> r;
			k *= m;

			h ^= k;
			h *= m;
		}

		int len3 = length & ~3;
		int left = length - len3;
		switch (left) {
			case 3:
				h ^= (long) (string.charAt(len3 + 2) & 0xffff) << 32;
			case 2:
				h ^= (long) (string.charAt(len3 + 1) & 0xffff) << 16;
			case 1:
				h ^= (long) (string.charAt(len3) & 0xffff);
				h *= m;
		}

		h ^= h >>> r;
		h *= m;
		h ^= h >>> r;

		return h;
	}

}
