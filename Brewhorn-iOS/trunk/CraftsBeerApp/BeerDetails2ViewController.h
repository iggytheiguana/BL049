
#import <UIKit/UIKit.h>
#import <FacebookSDK/FacebookSDK.h>
#import "FbGraph.h"
#import <Social/Social.h>

@interface BeerDetails2ViewController : UIViewController<UIActionSheetDelegate,UIPickerViewDelegate,UIPickerViewDataSource,FBLoginViewDelegate>
{
    
    NSMutableArray *arrayAromaTable;
    NSMutableArray *arraySweetTable;
    NSMutableArray *arrayBitterTable;
    NSMutableArray *arrayAromaValue;
    NSMutableArray *arrayBitterValue;
    NSMutableArray *arraySweetValue;
    NSMutableArray *arrayAdditiveValue;
    NSMutableArray *arrayDiscriptorValue;
    
    int chkArray;
    int chkDiscriptor;
    int chkTable;
    
    NSString *strStyleOldValue;
    NSString *strSelectDescriptor;
    UIActionSheet *actionSheet;
    UIPickerView *pickerViewStyle;
    int selectPicker;
    
    NSMutableArray *arrayPicker;
    NSMutableArray *likeArray1;
    NSMutableArray *likeArray2;
    NSMutableArray *likeArray3;
    NSMutableArray *arrayFruitBeer;
    
    NSMutableArray *arrayDiscriptorDetails;
    
    int componentValue;
    
    float valSour;
    float valAdditive;
    float valBooziness;
    
    int   chkButton;
    
    int aromaMin;
    int SweetMin;
    int maltMin;
    
    int aromaMax;
    int SweetMax;
    int maltMax;
    int strCompare;
    
    NSString *strSwchSour;
    NSString *strSwchAddictive;
    NSString *strSwchBooziness;
    NSString *strSour;
    NSString *strAddictive;
    NSString *strBooziness;
    
    NSString *someTweetFb;
    NSString *someTweet;
    NSString *strURL;
    
    BOOL fb;
}

@property (strong, nonatomic) IBOutlet UILabel *lblTop;
@property (strong, nonatomic) IBOutlet UILabel *lblHeader;
@property (strong, nonatomic) IBOutlet UILabel *lblHeader1;

@property (strong, nonatomic) IBOutlet UISlider *sliderSour;
@property (strong, nonatomic) IBOutlet UISlider *sliderAddictive;
@property (strong, nonatomic) IBOutlet UISlider *sliderBooziness;
@property (strong, nonatomic) NSString *valAroma;
@property (strong, nonatomic) NSString * valSweet;
@property (strong, nonatomic) NSString * valBitter;
@property (strong, nonatomic) NSString * valMalt;
@property (strong, nonatomic) NSString * valYeast;
@property (strong, nonatomic) NSString * valMouthFeel;
@property (strong, nonatomic) NSString *strChkUser;
@property (strong, nonatomic) NSString *striD;
@property (strong, nonatomic) NSMutableArray *array;
@property (strong, nonatomic) NSString *chkUser;
@property (strong, nonatomic) NSString *strAr;
@property (strong, nonatomic) NSString *strBi;
@property (strong, nonatomic) NSString *strSw;
@property (strong, nonatomic) NSString *txtAr;
@property (strong, nonatomic) NSString *txtBi;
@property (strong, nonatomic) NSString *txtSw;
@property (strong, nonatomic) NSMutableArray *likeArray4;

@property (strong, nonatomic) IBOutlet UILabel *lblAddictive;
@property (strong, nonatomic) IBOutlet UILabel *lblBooziness;

@property (strong, nonatomic) IBOutlet UILabel *lblAroma3;
@property (strong, nonatomic) IBOutlet UILabel *lblAroma4;
@property (strong, nonatomic) IBOutlet UILabel *lblAroma5;
@property (strong, nonatomic) IBOutlet UILabel *lblAroma6;
@property (strong, nonatomic) IBOutlet UILabel *lblAroma7;

@property (strong, nonatomic) IBOutlet UILabel *lblSweet3;
@property (strong, nonatomic) IBOutlet UILabel *lblSweet4;
@property (strong, nonatomic) IBOutlet UILabel *lblSweet5;
@property (strong, nonatomic) IBOutlet UILabel *lblSweet6;
@property (strong, nonatomic) IBOutlet UILabel *lblSweet7;
@property (strong, nonatomic) IBOutlet UILabel *lblBitter3;
@property (strong, nonatomic) IBOutlet UILabel *lblBitter4;
@property (strong, nonatomic) IBOutlet UILabel *lblBitter5;
@property (strong, nonatomic) IBOutlet UILabel *lblBitter6;
@property (strong, nonatomic) IBOutlet UILabel *lblBitter7;
@property (strong, nonatomic) IBOutlet UITextField *txtSour;
@property (strong, nonatomic) IBOutlet UITextField *txtAddictive;
@property (strong, nonatomic) IBOutlet UITextField *txtFlavour;
@property (strong, nonatomic) IBOutlet UITextField *txtWarmth;
@property (strong, nonatomic) IBOutlet UISwitch *switchSour;
@property (strong, nonatomic) IBOutlet UISwitch *switchAddictive;
@property (strong, nonatomic) IBOutlet UISwitch *switchBooziness;
@property (strong, nonatomic) IBOutlet UIScrollView *scrlView;
@property (strong, nonatomic) IBOutlet UITableView *tblSelectVal;
@property (strong, nonatomic) IBOutlet UIView *viewTbl;
@property (strong, nonatomic) IBOutlet UIButton *btnWarmth;
@property (strong, nonatomic) IBOutlet UIButton *btnFlavour;
@property (strong, nonatomic) IBOutlet UIButton *btnSour;
@property (strong, nonatomic) IBOutlet UIButton *btnAddi;
@property (strong, nonatomic) IBOutlet UIButton *btnInfo;
@property (strong, nonatomic) IBOutlet UIButton *additivebtn;
@property (strong, nonatomic) IBOutlet UIButton *boozinessbtn;
@property (strong, nonatomic) IBOutlet UITableView *tblNewView;
@property (strong, nonatomic) IBOutlet UIView *viewNew;

@property (retain, nonatomic) FbGraph *fbGraph;

@property (nonatomic, strong) ACAccountStore *accountStore;
@property (nonatomic, retain) ACAccount *facebookAccount;
@property (nonatomic, retain) ACAccount *twitterAccount;


- (IBAction)switch_Booziness:(id)sender;
- (IBAction)switch_sour:(id)sender;
- (IBAction)switch_Addictive:(id)sender;
- (IBAction)btnBack:(id)sender;
- (IBAction)btnSave:(id)sender;
- (IBAction)slider_Sour:(id)sender;
- (IBAction)slider_Addictive:(id)sender;
- (IBAction)slider_Booziness:(id)sender;
- (IBAction)btnClose:(id)sender;
- (IBAction)btn_Warmth:(id)sender;
- (IBAction)btn_Flavour:(id)sender;
- (IBAction)btn_Sour:(id)sender;
- (IBAction)btn_Addictive:(id)sender;
- (IBAction)btn_info:(id)sender;
- (IBAction)sour:(id)sender;
- (IBAction)addictive:(id)sender;
- (IBAction)booziness:(id)sender;
- (IBAction)btnCloseTblView:(id)sender;

@end
