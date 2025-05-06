(function () {
    app.beginUndoGroup("Split Text to Characters");

    var comp = app.project.activeItem;
    if (!(comp && comp instanceof CompItem)) {
        alert("Please select a composition.");
        return;
    }

    var selectedLayers = comp.selectedLayers;
    if (selectedLayers.length === 0 || !(selectedLayers[0] instanceof TextLayer)) {
        alert("Please select a text layer.");
        return;
    }

    var originalLayer = selectedLayers[0];
    var textDocument = originalLayer.text.sourceText.value;
    var numChars = textDocument.text.length;

    var baseTextProp = originalLayer.text.sourceText.value;

    // Create a temporary text layer to measure character widths
    var tempLayer = comp.layers.addText("W");
    tempLayer.enabled = false;
    tempLayer.moveBefore(originalLayer);

    var charX = 0;
    for (var i = 0; i < numChars; i++) {
        var ch = textDocument.text[i];

        // Skip invisible characters (like space, newline)
        if (ch === "\r" || ch === "\n") continue;

        var newLayer = originalLayer.duplicate();
        newLayer.name = "Char_" + ch + "_" + (i + 1);

        // Set only the current character as the text
        var newTextDoc = newLayer.text.sourceText.value;
        newTextDoc.text = ch;
        newLayer.text.sourceText.setValue(newTextDoc);

        // Use tempLayer to measure each character width up to current
        tempLayer.text.sourceText.setValue(baseTextProp);
        tempLayer.text.sourceText.setValue(baseTextProp.text.substring(0, i));
        var offset = tempLayer.sourceRectAtTime(comp.time, false).width;

        // Set the position based on offset
        var originalPos = originalLayer.property("Position").value;
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
