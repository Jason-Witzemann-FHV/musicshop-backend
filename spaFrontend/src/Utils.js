
export function toDuration(seconds) {
    let mins =parseInt( seconds / 60)
    let secs = seconds % 60
    if (secs <= 9) {
        secs = "0" + secs
    }
    return `${mins}:${secs}`
}

export function toPrice(double) {
    return `${parseFloat(double).toFixed(2)} €`
}