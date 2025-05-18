import time
from pywinauto import Application, findwindows
from pynput.keyboard import Controller, Key

keyboard = Controller()

def send_repo_chat_message(message: str, window_title_regex: str = r".*R\.E\.P\.O.*"):
    try:
        app = Application().connect(title_re=window_title_regex)
    except findwindows.ElementNotFoundError:
        print("Could not find a window matching", window_title_regex)
        return

    repo_win = app.window(title_re=window_title_regex)
    repo_win.set_focus()
    time.sleep(0.1)

    # Press T to open chat
    keyboard.press('t')
    keyboard.release('t')
    time.sleep(0.1)

    # Type message
    keyboard.type(message)
    time.sleep(0.1)

    # Press Enter to send
    keyboard.press(Key.enter)
    keyboard.release(Key.enter)

if __name__ == "__main__":
    chat_message = "<size=-11111><mark=#ffffff>FLASH"
    send_repo_chat_message(chat_message)
