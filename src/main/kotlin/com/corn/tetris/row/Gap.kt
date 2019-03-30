package com.corn.tetris.row

class Gap {
    val rows = ArrayList<TRow>()
    private var disappearCounter = 0

    val min: Int
        get() = rows.minBy { it -> it.idx }?.idx ?: -1

    val size: Int
        get() = rows.size

    fun add(r: TRow): Boolean {
        return if (rows.contains(r) || (rows.size != 0 && rows.none { Math.abs(it.idx - r.idx) == 1 }))
            false
        else
            rows.add(r)
    }

    fun disappear(listener: () -> Unit) {
        disappearCounter = rows.size
        rows.forEach { row ->
            row.disappear {
                if (--disappearCounter == 0)
                    listener()
            }
        }
    }
}