import email
import ssl
import smtplib
import argparse
from email.message import EmailMessage
#from verify_email import verify_email

def send_email(email_receiver):
    em = EmailMessage()
    em['From'] = "florin.florescu022@gmail.com"
    em['To'] = email_receiver
    em['Subject'] = "Email validation"
    em.set_content("Email to verify if ITS REALLY YOU")

    context = ssl.create_default_context()

    with smtplib.SMTP_SSL('smtp.gmail.com', 465, context=context) as smtp:
        smtp.login("florin.florescu022@gmail.com", "chnltzosquzeuzgx")
        smtp.sendmail("florin.florescu022@gmail.com", email_receiver, em.as_string())

    print("Emailul a fost trimis.")
    return "email trimis"

def main():
    parser = argparse.ArgumentParser(description='Send email via Gmail SMTP.')
    parser.add_argument('--receiver', required=True, help='Receiver email address')

    args = parser.parse_args()

    send_email(args.receiver)

if __name__ == '__main__':
    main()
