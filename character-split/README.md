# Split Text Layer Into Characters

This After Effects script takes a single text layer containing a word and splits it into individual text layers—one per character. Each character maintains the original font, size, and position relative to the original text.

## Features

- Splits a single text layer into multiple layers, one for each character.
- Retains the original text formatting (font, size, style).
- Positions each character accurately to match the original word layout.
- Supports both horizontal and vertical alignment.

## Installation

1. Save the script file with a `.jsx` or `.jsxbin` extension.
2. Place it in the After Effects **Scripts** folder:
   - Windows: `C:\Program Files\Adobe\Adobe After Effects <version>\Support Files\Scripts\`
   - macOS: `/Applications/Adobe After Effects <version>/Scripts/`
3. (Optional) Enable scripting UI:
   - In After Effects, go to **Edit > Preferences > Scripting & Expressions** (Windows) or **After Effects > Preferences > Scripting & Expressions** (macOS).
   - Check **Allow Scripts to Write Files and Access Network**.
4. Restart After Effects.

## Usage

1. Select a **single text layer** in your active composition that contains the word you want to split.
2. Run the script via:
   - **File > Scripts > character-split.jsx**
3. The script will generate new text layers, each containing one character from the original word, aligned accordingly.
