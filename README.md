# Data Encryption Standard

DES is a symmetric-key algorithm developed by IBM in the 1970's to meet the request of the National Bureau of Standards. It also employed what I believe to be one of the coolest concepts ever: Feistel ciphers.

This project is a java implementation of DES and Triple DES (3DES). Prior to developing the project in code, days were spent researching the Feistel function, key schedule, and the overall structure used in DES. Without any prior knowledge, the algorithm was quite complicated as it seemed to employ confusion and diffusion in almost a completely random way. However, once each of the steps are examined in detail, it is clear how DES's deterministic nature ultimately deemed it insecure as a standard.
