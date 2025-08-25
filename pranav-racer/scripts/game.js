const TICK_RATE = 60;
const TICK_INTERVAL = 1000 / TICK_RATE;

let lastTime = performance.now();
let delta = 0;

function gameLoop(now) {
    delta += now - lastTime;
    lastTime = now;

    // Process as many ticks as needed
    while (delta >= TICK_INTERVAL) {
        tick();
        delta -= TICK_INTERVAL;
    }
}

function tick() {
    console.log("Tick at", performance.now());
}
