var expectedValues = ['VOID', 'SEEDED', 'GROWING', 'TO HARVEST', 'HARVESTING'];
var value;
var state;
function run() {
    if (expectedValues.indexOf(value) != -1) {
        state.fieldsState = value;
    }
    return state;
}

