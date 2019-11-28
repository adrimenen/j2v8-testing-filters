var min;
var max;
var value;
var state;
function run() {
    if (value > min && value < max) {
        state.tempDevice = value;
    }
    return state;
}