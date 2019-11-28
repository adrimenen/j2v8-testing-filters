var min;
var max;
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