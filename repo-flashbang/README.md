# REPO Chat Flashbang

This Python script sends an automated chat message to a window with a title matching a specified regex pattern. It is designed for use with applications (like games or tools) that accept keyboard input through a focused chat window.

## Features

- Automatically focuses a target window by title (regex match).
- Simulates key presses to open chat, type a message, and send it.
- Easily customizable message and window title pattern.

## How to Use

1. Install VBCABLE's virtual microphone drivers. This is how the sound is passed through.
2. Install VoiceMeeter. This is how your own microphone input can be merged with the virtual mic's.
3. Set the microphone in R.E.P.O. to the VoiceMeeter B1 input.
4. Just run the script whenever.

## Requirements

VBCABLE Drivers
VoiceMeeter
Install dependencies with:

```bash
pip install -r requirements.txt
