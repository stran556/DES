# Encryption Standards

## DES

DES is a symmetric-key algorithm developed by IBM in the 1970's to meet the request of the National Bureau of Standards. It also employed what I believe to be one of the coolest concepts ever: Feistel ciphers.

This project is a java implementation of DES and Triple DES (3DES). Prior to developing the project in code, quite some time was spent researching the Feistel function, key schedule, and the overall structure used in DES. Without any prior knowledge, the algorithm seemed quite complicated as it seemed to employ confusion and diffusion in almost a completely random way. However, once each of the steps are examined in detail, DES is quite simple.

I have future plans to implement a function to toggle output viewing so one can visually walk through each of the steps and rounds. 

## AES

AES is a symmetric-key algorithm with roots in the Rijndael cipher that was established in 2001 as the new encryption standard to replace DES.

## RSA 

RSA is an asymmetric-key algorithm created by three mathematicians to solve the issue of unsecured secret-key transportation for symmetric-key algorithms across a network.
