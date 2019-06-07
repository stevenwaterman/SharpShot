package com.durhack.sharpshot.core.control

import com.durhack.sharpshot.core.state.tick.BulletMovement
import com.durhack.sharpshot.core.state.tick.Collision

class CollisionReport(val swap: List<Collision>,
                      val final: List<Collision>,
                      val survived: Set<BulletMovement>) {
    val bulletsToRemove =
            listOf(
                    swap.map { it.a.bullet },
                    swap.map { it.b.bullet },
                    final.map { it.a.bullet },
                    final.map { it.b.bullet }
                  ).flatten()

}

