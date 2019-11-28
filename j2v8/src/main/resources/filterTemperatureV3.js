var min = -5;
var max = 21;
var oldValue;
var newValue;
function run() {
    if (newValue > min && newValue < max) {
        return newValue;
    }
    else {
        return oldValue;
    }
}