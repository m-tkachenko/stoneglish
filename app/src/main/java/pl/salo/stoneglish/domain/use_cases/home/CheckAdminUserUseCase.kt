package pl.salo.stoneglish.domain.use_cases.home

import pl.salo.stoneglish.util.Constants.Companion.ADMIN_EMAIL
import pl.salo.stoneglish.util.Utils.same

class CheckAdminUserUseCase {
    operator fun invoke(userEmail: String?) =
        userEmail same ADMIN_EMAIL
}