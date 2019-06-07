package com.durhack.sharpshot.core.control

import com.durhack.sharpshot.core.state.BulletMovement
import com.durhack.sharpshot.core.state.Collision

class CollisionReport(val swap: Set<Collision>,
                      val final: Set<Collision>,
                      val survived: Set<BulletMovement>) {
    val bulletsToRemove =
            listOf(
                    swap.map { it.a.bullet },
                    swap.map { it.b.bullet },
                    final.map { it.a.bullet },
                    final.map { it.b.bullet }
                  ).flatten()

}

