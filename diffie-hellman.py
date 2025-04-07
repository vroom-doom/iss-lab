import random

G = 11
P = 17

def diffie_hellman():
    a = random.randint(1, 100)
    b = random.randint(1, 100)

    A = (G**a)%P
    B = (G**b)%P

    print(A, B)

    K1 = (A**b)%P
    K2 = (B**a)%P

    print(K1, K2)

    # Since Key 1 == Key 2, the algorithm succeeded at transferring the key


diffie_hellman()