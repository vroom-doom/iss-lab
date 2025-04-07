
IP = [
    58, 50, 42, 34, 26, 18, 10, 2,
    60, 52, 44, 36, 28, 20, 12, 4,
    62, 54, 46, 38, 30, 22, 14, 6,
    64, 56, 48, 40, 32, 24, 16, 8,
    57, 49, 41, 33, 25, 17, 9,  1,
    59, 51, 43, 35, 27, 19, 11, 3,
    61, 53, 45, 37, 29, 21, 13, 5,
    63, 55, 47, 39, 31, 23, 15, 7
]

FP = [
    40, 8, 48, 16, 56, 24, 64, 32,
    39, 7, 47, 15, 55, 23, 63, 31,
    38, 6, 46, 14, 54, 22, 62, 30,
    37, 5, 45, 13, 53, 21, 61, 29,
    36, 4, 44, 12, 52, 20, 60, 28,
    35, 3, 43, 11, 51, 19, 59, 27,
    34, 2, 42, 10, 50, 18, 58, 26,
    33, 1, 41, 9,  49, 17, 57, 25
]

E = [
    32, 1, 2, 3, 4, 5,
    4, 5, 6, 7, 8, 9,
    8, 9, 10,11,12,13,
    12,13,14,15,16,17,
    16,17,18,19,20,21,
    20,21,22,23,24,25,
    24,25,26,27,28,29,
    28,29,30,31,32,1
]

P = [
    16, 7, 20, 21,
    29, 12,28, 17,
    1, 15, 23, 26,
    5, 18, 31, 10,
    2, 8, 24, 14,
    32,27, 3, 9,
    19,13,30, 6,
    22,11, 4, 25
]

S_BOX = [
    [[14,4,13,1], [2,15,11,8], [3,10,6,12], [5,9,0,7]],
]

def generate_round_keys(key_64bit):
    return [key_64bit[i:i+48] for i in range(0, len(key_64bit), 48)]

def permute(block, table):
    return [block[x-1] for x in table]

def xor(a, b):
    return [i ^ j for i, j in zip(a, b)]

def str_to_bit_array(text):
    return [int(bit) for char in text for bit in format(ord(char), '08b')]

def bit_array_to_str(bit_array):
    chars = [bit_array[i:i+8] for i in range(0, len(bit_array), 8)]
    return ''.join([chr(int(''.join(map(str, byte)), 2)) for byte in chars])

def s_box_substitution(block_48bit):
    chunk = block_48bit[:6]
    row = (chunk[0] << 1) + chunk[5]
    col = (chunk[1] << 3) + (chunk[2] << 2) + (chunk[3] << 1) + chunk[4]
    val = S_BOX[0][row % 4][col % 4]  
    return [int(x) for x in format(val, '04b')] * 8  

def feistel(right, round_key):
    expanded = permute(right, E)
    xored = xor(expanded, round_key)
    substituted = s_box_substitution(xored)
    return permute(substituted, P)

def des_encrypt(plaintext, key):
    bits = str_to_bit_array(plaintext)
    key_bits = str_to_bit_array(key)
    block = permute(bits, IP)
    L, R = block[:32], block[32:]

    round_keys = generate_round_keys(key_bits)

    for i in range(16):  
        next_L = R
        next_R = xor(L, feistel(R, round_keys[i % len(round_keys)]))
        L, R = next_L, next_R

    combined = R + L
    final = permute(combined, FP)
    return bit_array_to_str(final)


plaintext = "ABCDEFGH"  
key = "12345678"       

ciphertext = des_encrypt(plaintext, key)
print(f"Encrypted: {ciphertext}")
