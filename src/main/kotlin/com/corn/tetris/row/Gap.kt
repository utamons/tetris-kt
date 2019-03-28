package com.corn.tetris.row

class Gap {
    private val rows = ArrayList<TRow>()

    fun add(r: TRow): Boolean {
        return if (rows.size != 0 && rows.none { Math.abs(it.idx - r.idx) == 1 })
            false
        else
            rows.add(r)
    }
}