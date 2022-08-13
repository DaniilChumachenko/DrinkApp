package com.chumachenko.drinkapp.app.di

import com.chumachenko.core.data.repository.CoreRepository
import com.chumachenko.core.domain.interactor.CoreInteractor
import com.chumachenko.core.ui.CoreViewModel
import com.chumachenko.drinkapp.app.domain.interactor.AppInteractor
import com.chumachenko.drinkapp.app.data.repository.AppRepository
import com.chumachenko.drinkapp.app.ui.AppViewModel

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val screenModule = module {

    //App
    factory { AppRepository(get()) }
    factory { AppInteractor(get()) }
    viewModel { AppViewModel(get()) }

    //Core
    factory { CoreRepository(get()) }
    factory { CoreInteractor(get()) }
    viewModel { CoreViewModel(get(), get()) }

//    factory {
//        AuthRepository(
//            get(),
//            get(),
//            get(),
//            get(),
//            get(),
//            get()
//        )
//    }
//    factory { AuthInteractor(get()) }
//    viewModel { PhoneSignInViewModel(get(), get(), get(), get()) }
//    viewModel { ImpactViewModel(get(), get(), get(), get()) }
//    viewModel { PhoneCodesViewModel(get(), get()) }
//    viewModel { ImpactCodesViewModel(get(), get()) }
//    viewModel { CodeVerificationViewModel(get(), get(), get(), get(), get()) }
//    viewModel { CodeImpactVerificationViewModel(get(), get(), get(), get(), get(), get(), get()) }
//    viewModel { DeepLinkAuthViewModel(get(), get(), get()) }
//
//    factory { StreamRepository(get(), get()) }
//    factory { StreamInteractor(get()) }
//    viewModel {
//        BroadcastingViewModel(
//            get(),
//            get<Context>() as Application,
//            get(),
//            get(),
//            get(),
//            get()
//        )
//    }
//
//    factory { CompilationRepository(get(), get(), get()) }
//    factory { CompilationInteractor(get()) }
//
//    single { ProfileRepository(get(), get()) }
//    single { ProfileInteractor(get()) }
//    viewModel { ProfileViewModel(get(), get(), get(), get(), get(), get(), get()) }
//    viewModel { TokensHoldersViewModel(get(), get(), get()) }
//    viewModel { WalletViewModel(get(), get(), get()) }
//    viewModel { ShareViewModel(get(), get(), get()) }
//    viewModel { LeaderboardViewModel(get(), get(), get()) }
//    viewModel { NotificationViewModel(get(), get(), get()) }
//    viewModel { ProjectViewModel(get(), get(), get(), get()) }
//    viewModel { ShareOfRevenueViewModel(get(), get(), get()) }
//    viewModel { CreatorsViewModel(get(), get(), get()) }
//    viewModel { VideoStatsViewModel(get(), get(), get()) }
//    viewModel { IncomeViewModel(get(), get(), get()) }
//    viewModel { StatsViewsViewModel(get(), get()) }
//    viewModel { StatsMonthViewModel(get(), get(), get()) }
//    viewModel { StatsActionsViewModel(get()) }
//    viewModel { EditAccountViewModel(get(), get(), get(), get(), get(), get(), get(), get()) }
//    viewModel { ReportContentViewModel(get(), get()) }
//    viewModel { ReportPageViewModel(get(), get()) }
//    viewModel { CoverViewModel(get(), get(), get(), get(), get(), get()) }
//    viewModel { ReportsViewModel(get(), get(), get()) }
//    viewModel { BusinessPagesViewModel(get(), get(), get()) }
//    viewModel { TotalMonthRevenueViewModel(get(), get(), get()) }
//
//    factory { HotelInfoRepository(get(), get()) }
//    factory { HotelInfoInteractor(get()) }
//
//    viewModel { SearchLocationViewModel(get(), get(), get(), get()) }
//    viewModel { SearchPlaceViewModel(get(), get(), get(), get()) }
//    viewModel { CompilationLocationViewModel(get(), get(), get(), get()) }
//    viewModel { CompilationCartViewModel(get(), get(), get(), get()) }
//    viewModel { OptionsDialogItemViewModel(get(), get(), get(), get()) }
//    viewModel { CompilationActionsViewModel(get(), get(), get()) }
//    viewModel { EditItemViewModel(get(), get(), get()) }
//    viewModel { CompilationPhotosViewModel(get(), get(), get()) }
//    viewModel { ZoomViewModel(get(), get(), get(), get(), get()) }
//    viewModel { ProfileTypeViewModel(get(), get(), get(), get()) }
//
//    viewModel { FeedViewModel(get(), get(), get(), get(), get(), get(), get(), get(), get()) }
//
//    single { AskRepository(get(), get()) }
//    single { AskInteractor(get()) }
//    viewModel { AskItemsViewModel(get(), get(), get(), get()) }
//    viewModel { ChatListViewModel(get(), get(), get()) }
//    viewModel { ChatViewModel(get(), get(), get(), get(), get(), get()) }
//    viewModel { ChatZoomViewModel(get(), get(), get()) }
//    viewModel {
//        PlayerViewModel(
//            get(),
//            get(),
//            get(),
//            get(),
//            get(),
//            get(),
//            get(),
//            get(),
//            get(),
//            get()
//        )
//    }
//
//    single { SearchCache() }
//    single { SearchStritchCache() }
//    single { SearchRepository(get(), get(), get(), get()) }
//    single { SearchInteractor(get()) }
//    viewModel { SearchViewModel(get(), get(), get(), get(), get()) }
//    viewModel { TabPagesViewModel(get(), get()) }
//    viewModel { TabBlocksViewModel(get(), get(), get()) }
//    viewModel { TabStritchesViewModel(get(), get(), get()) }
//    viewModel { StritchViewModel(get(), get(), get(), get(), get(), get()) }
//    viewModel { ChangeDomainViewModel(get(), get(), get(), get(), get(), get()) }
//
//    single { StartRepository(get(), get()) }
//    single { StartInteractor(get()) }
//    viewModel { StartViewModel(get(), get(), get(), get(), get(), get(), get()) }
//
//    single { DomainRepository(get(), get()) }
//    single { DomainInteractor(get()) }
//    viewModel { MyDomainViewModel(get(), get(), get()) }
//    viewModel { CreateViewModel(get(), get(), get()) }
//    viewModel { TypeViewModel(get(), get(), get()) }
//    viewModel { OpenViewModel(get(), get(), get()) }
//
//    viewModel { SavedStritchesViewModel(get(), get(), get(), get(), get(), get(), get()) }
//    viewModel { SavedPinnedViewModel(get(), get(), get(), get(), get(), get(), get()) }
//
//    viewModel { DomainWelcomeViewModel(get(), get()) }
//
//    viewModel { OverviewViewModel(get(), get()) }
}
