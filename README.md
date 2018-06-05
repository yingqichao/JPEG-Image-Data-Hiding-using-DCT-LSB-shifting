# JPEG-Reversible-Data-Hiding-using-DCT-LSB-shifting
The purpose is to hide a JPEG file A into another JPEG file B by altering some high-frequent DCT coefficients of B.
Here A is read as bitstream. 

The flow of the files is:  img.java -> jpeglibrary.java ->HideScheme2.java -> (to the recipient) -> ExtractScheme.java.

img.java is the main function that contains a "public static void main". 
calctool.java has defined some useful functions.
jpeglibrary.java is a simplified JPEG format reading file that not only decodes the header into JPEG information according to their tags and, based on these tags, the several Huffman trees, DRI, etc., but also calls HideScheme2.java to do the embedding. 
HideScheme2.java gets new DCT coefficients and re-encode them to form bitstream of the output file. Note that here we do not modify the Huffman trees to further shorten the length of output bitstream.
ExtractScheme.java extracts hidden information, which will be transformed into "decode.jpg" automatically. 

If you wish to separate hide and extract,then
The data hider should have at least "img.java,jpeglibrary.java,HideScheme2.java,calctool.java"
The recipient should have "ExtractScheme.java,calctool.java". Necessary functions in jpeglibrary.java can also be found in ExtractScheme.java. 

All the data hider/the recipient need to do is to give the name of host image+to-be-embedded image/Embedded image in img.java/ExtractScheme.java. 

The code is robust to typical kinds of JPEG settings (gray/YCrCb, different quantization level,etc.).Usually using the least one significant bit is okay for embedding a JPEG to another with a same size, and the distortion we introduced is tiny.

Special thank to the guidance of JPEG format knowledge offered by FlyingFish on Github. https://blog.csdn.net/zhengzhoudaxue2/article/details/7693258


Please kindly contact me if you have any suggestion/new idea/comment or find any bug while using the codes via shinydotcom@163.com. And I will quickly reply.
