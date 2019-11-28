var min = -5;
var max = 21;
var value;
var state;
function run() {
    if (value > min && value < max) {
        state.tempDevice = value;
    }
    return state;
}
["tempDevice"]