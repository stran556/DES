# Encryption Standards

This project includes my Java implementations for DES/3DES, AES-128, RSA, and SHA-256. I took as much, if not more, enjoyment from learning about the design ingenuity of these standards than actually successfully implementing them in code. It's one thing to know that RSA is accepted as a secure public-key system, but it's another thing to understand the role that large prime factorization plays in creating keys that offer not only confidentiality, but also authenticity, integrity, and non-repudiation through digital signatures. 

This program is designed to be run using any Java compiler. After having used 'openssl' in Linux a couple times, I found it too tedious to relearn the proper formatting for performing simple operations after I had forgotten them. I wrote this program to accomplish a task similar to openssl with some slight changes. For simplicity reasons, it is intended to be operated by navigating a menu via user input. I also found the diffusion and confusion tactics in AES so interesting that an option is provided to toggle printing of step-by-step operations, like a looking glass. Currently, I am looking to do the same for DES while also working on a feature for RSA to allow the user to generate large primes to be used for locally creating a key.

![](https://github.com/stran556/encryption-standards/blob/main/aes_output.png)
