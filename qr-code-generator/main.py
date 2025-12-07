import segno

qrcode = segno.make_qr("https://noahham.com/")
qrcode.save("qr-code.png", scale=5)