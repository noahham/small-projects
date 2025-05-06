// Split selected text layer into one layer per character
(function () {
    app.beginUndoGroup("Split Text to Characters");

    let comp = app.project.activeItem;
    if (!(comp && comp instanceof CompItem)) {
        alert("Please select a composition.");
        return;
    }

    let selectedLayers = comp.selectedLayers;
    if (selectedLayers.length === 0 || !(selectedLayers[0] instanceof TextLayer)) {
        alert("Please select a text layer.");
        return;
    }

    let originalLayer = selectedLayers[0];
    let textDocument = originalLayer.text.sourceText.value;
    let numChars = textDocument.text.length;

    let baseTextProp = originalLayer.text.sourceText.value;

    // Create a temporary text layer to measure character widths
    let tempLayer = comp.layers.addText("W");
    tempLayer.enabled = false;
    tempLayer.moveBefore(originalLayer);

    let charX = 0;
    for (let i = 0; i < numChars; i++) {
        let char = textDocument.text[i];

        // Skip invisible characters (like space, newline)
        if (char === "\r" || char === "\n") continue;

        let newLayer = originalLayer.duplicate();
        newLayer.name = "Char_" + char + "_" + (i + 1);

        // Set only the current character as the text
        let newTextDoc = newLayer.text.sourceText.value;
        newTextDoc.text = char;
        newLayer.text.sourceText.setValue(newTextDoc);

        // Use tempLayer to measure each character width up to current
        tempLayer.text.sourceText.setValue(baseTextProp);
        tempLayer.text.sourceText.setValue(baseTextProp.text.substring(0, i));
        let offset = tempLayer.sourceRectAtTime(comp.time, false).width;

        // Set the position based on offset
        let originalPos = originalLayer.property("Position").value;
        newLayer.property("Position").setValue([
            originalPos[0] + offset,
            originalPos[1]
        ]);
    }

    // Clean up
    tempLayer.remove();
    originalLayer.enabled = false;

    app.endUndoGroup();
})();
