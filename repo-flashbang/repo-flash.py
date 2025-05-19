import time
from pywinauto import Application, findwindows
from pynput.keyboard import Controller, Key
import pyaudio
import wave
import time
import sys
import os

def list_audio_devices():
    """List all available audio input and output devices"""
    p = pyaudio.PyAudio()
    print("\nAVAILABLE AUDIO DEVICES:")
    print("=======================")
    
    print("INPUT DEVICES:")
    for i in range(p.get_device_count()):
        dev_info = p.get_device_info_by_index(i)
        if dev_info["maxInputChannels"] > 0:
            print(f"{i}: {dev_info['name']} (Channels: {dev_info['maxInputChannels']})")
    
    print("\nOUTPUT DEVICES:")
    for i in range(p.get_device_count()):
        dev_info = p.get_device_info_by_index(i)
        if dev_info["maxOutputChannels"] > 0:
            print(f"{i}: {dev_info['name']} (Channels: {dev_info['maxOutputChannels']})")
    
    p.terminate()

def find_device_index(device_name, for_output=True):
    """Find device index by partial name match"""
    p = pyaudio.PyAudio()
    result = None
    
    for i in range(p.get_device_count()):
        dev_info = p.get_device_info_by_index(i)
        if device_name.lower() in dev_info["name"].lower():
            # For output devices, check maxOutputChannels
            if for_output and dev_info["maxOutputChannels"] > 0:
                result = i
                break
            # For input devices, check maxInputChannels
            elif not for_output and dev_info["maxInputChannels"] > 0:
                result = i
                break
    
    p.terminate()
    return result

def get_device_channel_count(device_index, for_output=True):
    """Get the number of channels a device supports"""
    p = pyaudio.PyAudio()
    dev_info = p.get_device_info_by_index(device_index)
    channels = dev_info["maxOutputChannels"] if for_output else dev_info["maxInputChannels"]
    p.terminate()
    return channels

def play_audio_file_through_device(file_path, output_device_index):
    """Play audio file through specified output device"""
    if not os.path.exists(file_path):
        print(f"Error: File '{file_path}' not found!")
        return False
    
    try:
        # Open the wave file
        wf = wave.open(file_path, 'rb')
        
        # Get the audio file properties
        file_channels = wf.getnchannels()
        sample_width = wf.getsampwidth()
        frame_rate = wf.getframerate()
        
        # Get device channel count
        device_channels = get_device_channel_count(output_device_index)
        
        # Print debug info
        print(f"\nAudio file: {file_path}")
        print(f"  - Channels: {file_channels}")
        print(f"  - Sample Width: {sample_width} bytes")
        print(f"  - Frame Rate: {frame_rate} Hz")
        print(f"Output device (index {output_device_index}) supports {device_channels} channels")
        
        # Initialize PyAudio
        p = pyaudio.PyAudio()
        
        # Open a stream on the specified output device
        # Use the minimum channel count between file and device
        channels_to_use = min(file_channels, device_channels)
        
        stream = p.open(format=p.get_format_from_width(sample_width),
                        channels=channels_to_use,
                        rate=frame_rate,
                        output=True,
                        output_device_index=output_device_index)
        
        # Read data in chunks and play it
        print(f"Playing audio through device index {output_device_index}...")
        chunk_size = 1024
        data = wf.readframes(chunk_size)
        
        while len(data) > 0:
            stream.write(data)
            data = wf.readframes(chunk_size)
        
        # Close everything
        stream.stop_stream()
        stream.close()
        wf.close()
        p.terminate()
        print("Finished playing audio")
        
    except Exception as e:
        print(f"Error playing audio: {str(e)}")
        return False

def main():
    # Default file path - can be changed
    file_path = "sfx.wav"
    virtual_mic_name = "CABLE INPUT"
    
    # Ask for file if not provided or not found
    if len(sys.argv) > 1:
        file_path = sys.argv[1]
    
    if not os.path.exists(file_path):
        print(f"File '{file_path}' not found. Please enter the path to your sound file:")
        file_path = input("> ").strip()
    
    # Find the CABLE Output device index (this is what we want to play through)
    cable_device_index = find_device_index(virtual_mic_name)
    
    if cable_device_index is None:
        print("\nCould not find CABLE Input device.")
    
    # Play audio through the selected device
    play_audio_file_through_device(file_path, cable_device_index)
    

def send_repo_chat_message(message: str, window_title_regex: str = r".*R\.E\.P\.O.*"):
    try:
        app = Application().connect(title_re=window_title_regex)
    except findwindows.ElementNotFoundError:
        print("Could not find a window matching", window_title_regex)
        return

    repo_win = app.window(title_re=window_title_regex)
    repo_win.set_focus()
    time.sleep(0.01)

    keyboard.press('t')
    keyboard.release('t')
    time.sleep(0.01)

    keyboard.type(message)
    time.sleep(0.01)

    keyboard.press(Key.enter)
    keyboard.release(Key.enter)

if __name__ == "__main__":
    main()
    
    keyboard = Controller()
    chat_message = "<size=-11111><mark=#ffffff>FLASH"
    send_repo_chat_message(chat_message)
