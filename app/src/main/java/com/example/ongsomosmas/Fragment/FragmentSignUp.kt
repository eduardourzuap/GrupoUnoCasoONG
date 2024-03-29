package com.example.ongsomosmas.Fragment

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.ongsomosmas.Dto.Register
import com.example.ongsomosmas.R
import com.example.ongsomosmas.databinding.FragmentSignupBinding
import com.example.ongsomosmas.views.MainViewModel
import com.example.ongsomosmas.views.VideModelFactory
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class FragmentSignUp : Fragment() {

    private lateinit var binding: FragmentSignupBinding
    private val viewModel: MainViewModel by viewModels(
        factoryProducer = { VideModelFactory(requireContext()) }
    )

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSignupBinding.inflate(inflater, container, false)

        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
            findNavController().navigateUp()
        }

        binding.btRedirectLogin.setOnClickListener {
            findNavController().navigateUp()
        }

        /*Boton login inactivo*/
        binding.btRegisterButton.isEnabled = false

        /*Validando cada campo del register*/
        binding.tiNameLastname.editText?.addTextChangedListener {
            viewModel.validateRegister(
                binding.tiEmail.editText?.text.toString(),
                binding.tiPassword.editText?.text.toString(),
                binding.tiRepeatPassword.editText?.text.toString(),
                binding.tiNameLastname.editText?.text.toString()
            )
        }
        binding.tiEmail.editText?.addTextChangedListener {
            viewModel.validateRegister(
                binding.tiEmail.editText?.text.toString(),
                binding.tiPassword.editText?.text.toString(),
                binding.tiRepeatPassword.editText?.text.toString(),
                binding.tiNameLastname.editText?.text.toString()
            )
        }
        binding.tiPassword.editText?.addTextChangedListener {
            viewModel.validateRegister(
                binding.tiEmail.editText?.text.toString(),
                binding.tiPassword.editText?.text.toString(),
                binding.tiRepeatPassword.editText?.text.toString(),
                binding.tiNameLastname.editText?.text.toString()
            )
        }

        binding.tiRepeatPassword.editText?.addTextChangedListener {
            viewModel.validateRegister(
                binding.tiEmail.editText?.text.toString(),
                binding.tiPassword.editText?.text.toString(),
                binding.tiRepeatPassword.editText?.text.toString(),
                binding.tiNameLastname.editText?.text.toString()
            )
        }

        /*observando viewmodel para bloquear o desbloquear boton*/
        viewModel.enableRegister.observe(viewLifecycleOwner) { value ->
            if (value) {
                binding.btRegisterButton.isEnabled = value
            }
        }

        binding.tiRepeatPassword.editText?.addTextChangedListener {
            viewModel.samePasswordRepeat(
                binding.tiPassword.editText?.text.toString().trim(),
                binding.tiRepeatPassword.editText?.text.toString().trim()
            )
        }

        binding.tiPassword.editText?.addTextChangedListener {
            viewModel.samePasswordRepeat(
                binding.tiPassword.editText?.text.toString().trim(),
                binding.tiRepeatPassword.editText?.text.toString().trim()
            )
        }
        viewModel.loading.observe(viewLifecycleOwner) { value ->
            if (value) {
                binding.btRegisterButton.isEnabled = false
                binding.progressCircular.visibility = View.GONE
            } else {
                binding.btRegisterButton.isEnabled = true
                binding.progressCircular.visibility = View.GONE
            }
        }


        /*observando password para que sean iguales*/
        viewModel.samePassword.observe(viewLifecycleOwner) { value ->
            if (value) {
                binding.tiRepeatPassword.error = ""
                binding.tiPassword.error = ""
            } else
                binding.tiRepeatPassword.error = getString(R.string.notSamePassword)
        }

        binding.btRegisterButton.setOnClickListener {

            viewModel.registerUser(
                Register(
                    binding.tiNameLastname.editText?.text.toString(),
                    binding.tiEmail.editText?.text.toString(),
                    binding.tiPassword.editText?.text.toString()
                )

            )
            binding.progressCircular.visibility = View.VISIBLE
            binding.btRegisterButton.isEnabled = false
        }
        viewModel.success.observe(viewLifecycleOwner) { response ->
            if (response) {
                binding.progressCircular.visibility = View.GONE
                dialogAlertRegister(getString(R.string.bodyRegisterOK))
                binding.btRegisterButton.isEnabled = true

            }
        }
        viewModel.error.observe(viewLifecycleOwner) { response ->
            if (response != null) {
                dialogAlert(getString(R.string.bodyErrorRegister))
                binding.btRegisterButton.isEnabled = true
                binding.progressCircular.visibility = View.GONE
            }
        }

        binding.tiNameLastname.editText?.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(
                s: CharSequence?,
                start: Int,
                count: Int,
                after: Int
            ) {

            }

            override fun onTextChanged(
                s: CharSequence?,
                start: Int,
                before: Int,
                count: Int
            ) {
            }

            override fun afterTextChanged(s: Editable?) {

                binding.tiNameLastname.error = ""

            }

        })
        binding.tiEmail.editText?.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(
                s: CharSequence?,
                start: Int,
                count: Int,
                after: Int
            ) {

            }

            override fun onTextChanged(
                s: CharSequence?,
                start: Int,
                before: Int,
                count: Int
            ) {
            }

            override fun afterTextChanged(s: Editable?) {

                binding.tiEmail.error = ""

            }

        })
        binding.tiPassword.editText?.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(
                s: CharSequence?,
                start: Int,
                count: Int,
                after: Int
            ) {

            }

            override fun onTextChanged(
                s: CharSequence?,
                start: Int,
                before: Int,
                count: Int
            ) {
            }

            override fun afterTextChanged(s: Editable?) {

                binding.tiPassword.error = ""

            }

        })


        return binding.root
    }

    private fun clearTextSignUp() {
        binding.tiNameLastname.editText?.text?.clear()
        binding.tiEmail.editText?.text?.clear()
        binding.tiPassword.editText?.text?.clear()
        binding.tiRepeatPassword.editText?.text?.clear()
    }

    private fun warnError() {
        binding.tiNameLastname.error = (getString(R.string.wrongName))
        binding.tiEmail.error = (getString(R.string.wrongEmail))
        binding.tiPassword.error = (getString(R.string.wrongPassword))
        binding.tiRepeatPassword.error = (getString(R.string.wrongPasswordRepeat))
    }

    private fun dialogAlert(body: String) {
        context?.let {
            MaterialAlertDialogBuilder(it)
                .setTitle(getString(R.string.titleError))
                .setMessage(body)
                .setPositiveButton(
                    getString(R.string.buttonOk)
                ) { _, _ ->
                    clearTextSignUp()
                    warnError()
                }
                .show()
        };
    }

    private fun dialogAlertRegister(body: String) {
        context?.let {
            MaterialAlertDialogBuilder(it)
                .setTitle(getString(R.string.titleRegister))
                .setMessage(body)
                .setPositiveButton(
                    getString(R.string.buttonOk)
                ) { _, _ ->
                    findNavController().navigateUp()
                }
                .show()
        };
    }
}