package com.corn.tetris.row

class Gap {
    private val rows = ArrayList<TRow>()

    fun add(r: TRow): Boolean{
        for (row in rows) {
            if (Math.abs(row.idx - r.idx) == 1) {
                rows.add(r)
                return true
            }
        }
        return false
    }
}