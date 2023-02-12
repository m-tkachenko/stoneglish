package pl.salo.stoneglish.presentation.core.profile

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint
import pl.salo.stoneglish.common.Resource
import pl.salo.stoneglish.databinding.FragmentProfileBinding
import pl.salo.stoneglish.util.coreNavigator

@AndroidEntryPoint
class ProfileFragment : Fragment() {

    lateinit var binding: FragmentProfileBinding
    private val viewModel: ProfileViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getCurrentUser()

        observeCurrentUser()

        binding.profileInformation.setOnClickListener {
            coreNavigator().goToInformation()
        }

        binding.profileFavoriteTopics.setOnClickListener {
            coreNavigator().goToFavoriteTopics()
        }

        binding.changeNameBtn.setOnClickListener {
            viewModel.selectField(Field.NAME)
            coreNavigator().goToChangeFieldScreen()
        }

        binding.changeAgeBtn.setOnClickListener {
            viewModel.selectField(Field.AGE)
            coreNavigator().goToChangeFieldScreen()
        }

        binding.profileSignOut.setOnClickListener {
            coreNavigator().signOut()
        }
    }

    @SuppressLint("SetTextI18n")
    private fun observeCurrentUser() {
        viewModel.currentUser.observe(viewLifecycleOwner) {
            when (it) {
                is Resource.Loading -> {
                    binding.profileProgressBar.visibility = View.VISIBLE
                    binding.profileContainer.visibility = View.GONE
                }
                is Resource.Success -> {
                    binding.accountInfoAge.text = it.data?.age.toString()
                    binding.accountInfoEmail.text = it.data?.email
                    binding.accountInfoName.text = it.data?.username
                    val eLevel = "Your level"
                    binding.englishLevel.text = "$eLevel: ${it.data?.englishLevel}"
                    binding.profileUsername.text = it.data?.username
                    if (it.data?.imageUrl != null) {
                        binding.profileUserImage.visibility = View.VISIBLE
                        binding.profileImageFirstLetter.visibility = View.GONE


                        Glide.with(requireContext()).load(it.data.imageUrl)
                            .centerCrop()
                            .into(binding.profileUserImage)
                    } else {
                        binding.profileUserImage.visibility = View.GONE
                        binding.profileImageFirstLetter.text = it.data?.username?.first().toString()
                        binding.profileImageFirstLetter.visibility = View.VISIBLE
                    }
                    binding.profileProgressBar.visibility = View.GONE
                    binding.profileContainer.visibility = View.VISIBLE
                }
                is Resource.Error -> {
                    binding.profileProgressBar.visibility = View.GONE
                    Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
                    viewModel.getCurrentUser()
                }
            }
        }
    }
}