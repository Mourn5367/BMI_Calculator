package kr.ac.kopo.bmi_calculator;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import java.util.ArrayList;
import java.util.List;

import kr.ac.kopo.bmi_calculator.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

        // 바인드
        private ActivityMainBinding binding;
        
        // 결과 메시지 조작하기 위한 객체
        private SpannableStringBuilder ssb = new SpannableStringBuilder();
        
        // 어떤 에러 텍스트인지 수집하는 배열
        List<String> errorText = new ArrayList<>();
        
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            //  바인딩 객체 초기화
            binding = ActivityMainBinding.inflate(getLayoutInflater());

            //  레이아웃 뷰 설정
            setContentView(binding.getRoot());
            binding.btnCal.setOnClickListener(btnCalClickListener);
            binding.btnReset.setOnClickListener(btnResetClickListener);
            binding.btnGoToSearch.setOnClickListener(btnGoToSearchListener);
            binding.lottieView.addAnimatorListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animation)
                {
                    // 애니메이션 시작 시
                }

                @Override
                public void onAnimationEnd(Animator animation) {
                    // 애니메이션 끝났을 때 실행될 코드
                    // 에러 로티 끝나면 러닝 로티로 변경후 무한반복 (기본 메인화면으로)
                    if ("error".equals(binding.lottieView.getTag()))
                    {
                        binding.lottieView.setTag("idle");
                        binding.lottieView.setAnimation(R.raw.running);
                        binding.lottieView.setRepeatCount(ValueAnimator.INFINITE);
                        binding.lottieView.playAnimation();
                    }
                }

                @Override
                public void onAnimationCancel(Animator animation) {
                    // 애니메이션 취소됐을 때
                }

                @Override
                public void onAnimationRepeat(Animator animation) {
                    // 애니메이션 반복될 때
                }
            });

        }
        View.OnClickListener btnGoToSearchListener = new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(MainActivity.this, FoodSearchActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT); // 이미 있으면 앞으로
                startActivity(intent);
            }
        };

        // 초기화 버튼 누를시 에디트 텍스트 내용과 공백으로 만들고, 결과창은 안보이게 변경 후, 로티 실행
        View.OnClickListener btnResetClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                EditText[] inputFields = {
                        binding.inputName,
                        binding.inputHeight,
                        binding.inputWeight
                };

                for( EditText text : inputFields)
                {
                    text.setText("");
                }

                binding.resultLayer.setVisibility(View.GONE);
                binding.lottieView.setVisibility(View.VISIBLE);
                binding.lottieView.setAnimation(R.raw.running);
                binding.lottieView.playAnimation();
                binding.lottieView.setTag("idle");
                binding.lottieView.setRepeatCount(ValueAnimator.INFINITE);
            }
        };




        // 입력된 값이 비어져 있는 경우 오류 표시
        // 정상적인 경우 결과창 보여줌
        View.OnClickListener btnCalClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // 에디트 박스 가져올 배열 (성명, 신장, 몸무게)
                EditText[] inputFields = {
                        binding.inputName,
                        binding.inputHeight,
                        binding.inputWeight
                };
                
                // 토스트로 어떤 항목이 안넣어졌는데 표시하기 위한 텍스트 값들
                String[] fieldNames = {
                        getString(R.string.name),
                        getString(R.string.height),
                        getString(R.string.weight)
                };
                
                
                // 저장된 BMI 등급 텍스트들
                String[] bmiValues = {
                        getString(R.string.underWeight),
                        getString(R.string.normalWeight),
                        getString(R.string.overWeight),
                        getString(R.string.obeseWeight),
                        getString(R.string.severelyObeseWeight),
                };
                
                // 결과 텍스트가 보여줄지 말지 설정한 변수
                boolean hasError = false;

                // 입력된 값들을 가져와서 공백으로 비어진 경우 에러메시지를 담고 화면에 표시.
                for (int i = 0; i < inputFields.length; i++) {
                    try {
                        String text = inputFields[i].getText().toString();

                        if (TextUtils.isEmpty(text)) {
                            Log.e("입력오류", fieldNames[i] + " 입력 안됨");
                            inputFields[i].setError(fieldNames[i] + "을(를) 입력해주세요");
                            errorText.add(fieldNames[i]);
                            hasError = true;
                        }

                    } catch (Exception e) {
                        Log.e("입력오류", fieldNames[i] + " 처리 중 예외: " + e);
                        hasError = true;
                    }
                }
                
                // 에디트텍스트중 공백이 있을경우 에러 메시지를 토스트로도 출력 후 에러 로티 재생
                if (hasError)
                {
                    String text = TextUtils.join(", ", errorText);
                    Toast.makeText(MainActivity.this,  text+"을(를) 입력해주세요", Toast.LENGTH_SHORT).show();
                    errorText.clear();
                    binding.lottieView.setAnimation(R.raw.error);
                    binding.lottieView.playAnimation();
                    binding.lottieView.setRepeatCount(1);
                    binding.lottieView.setTag("error");
                }
                else
                {
                    // 에디트 텍스트 값들 가져옴
                    String name = inputFields[0].getText().toString();
                    double height = Double.parseDouble(inputFields[1].getText().toString());
                    double weight = Double.parseDouble(inputFields[2].getText().toString());

                    double bmiResult = weight/ Math.pow((height/100.0),2);
                    String formattedBmiResult = String.format("%.2f", bmiResult);
                    String resultBMI = "";
                    String resultAdvice = "";

                    int bmiColor = R.color.black;
                    BmiCategory bmiGrade;
                    //BMI 계산된 값 가져와서 Enum 범위안에 어느곳에 해당되는지 판별
                    bmiGrade = BmiCategory.fromBmi(bmiResult);
                    
                    // BMI 결과를 보여주기 위해 로티 안보이게
                    binding.lottieView.setVisibility(View.GONE);

                    switch(bmiGrade)
                    {
                        case UNDERWEIGHT:
                            resultBMI = bmiValues[0];
                            binding.imageResult.setImageResource(R.drawable.result_underweight);
                            bmiColor = R.color.blue;
                            resultAdvice = getString(R.string.underWeightDescribe);
                            break;
                        case NORMAL:
                            resultBMI = bmiValues[1];
                            binding.imageResult.setImageResource(R.drawable.result_normalweight);
                            bmiColor = R.color.green;
                            resultAdvice = getString(R.string.normalWeightDescribe);
                            break;
                        case OVERWEIGHT:
                            resultBMI = bmiValues[2];
                            binding.imageResult.setImageResource(R.drawable.result_overweight);
                            bmiColor = R.color.orange;
                            resultAdvice = getString(R.string.overWeightDescribe);
                            break;
                        case OBESE:
                            resultBMI = bmiValues[3];
                            binding.imageResult.setImageResource(R.drawable.result_obeseweight);
                            bmiColor = R.color.red;
                            resultAdvice = getString(R.string.obeseWeightDescribe);
                            break;
                        case SEVERELYOBESE:
                            resultBMI = bmiValues[4];
                            binding.imageResult.setImageResource(R.drawable.result_severelyobeseweight);
                            bmiColor = R.color.brown;
                            resultAdvice = getString(R.string.severelyObeseWeightDescribe);
                            break;

                    }



                    binding.resultLayer.setVisibility(View.VISIBLE);
                    // BMI 결과 텍스트 조합
                    String part1 = name + "님의 키는 " + height + "cm, 몸무게는 " + weight + "kg 입니다.\n따라서 "
                            + name +"님의 BMI는 " + formattedBmiResult+ "kg/㎡, ";
                    String part2 = resultBMI;
                    String part3 = "으로 측정되었습니다." +"\n"+resultAdvice;

                    // 전체 문장 생성
                    ssb.clear();
                    ssb.clearSpans();

                    ssb.append(part1);
                    int start = ssb.length();  // 색상 적용 시작 위치
                    ssb.append(part2);
                    int end = ssb.length();    // 색상 적용 끝 위치
                    ssb.append(part3);

                    // 색상 적용
                    int colorInt = ContextCompat.getColor(MainActivity.this, bmiColor);  // R.color.xxx
                    ssb.setSpan(new ForegroundColorSpan(colorInt), start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                    // 크기 조절
                    float sizeInSp = getResources().getDimension(R.dimen.resultBmiTextSize) / getResources().getDisplayMetrics().scaledDensity;
                    ssb.setSpan(new AbsoluteSizeSpan((int) sizeInSp, true), start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                    // 굵기 설정 (볼드)
                    ssb.setSpan(new StyleSpan(android.graphics.Typeface.BOLD), start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                    // 텍스트에 적용
                    binding.textResult.setText(ssb);

                }
            }
        };
        
        // 스태틱에 있는것들 다부숨
    @Override
    protected void onDestroy() {
        super.onDestroy();

        // 바인딩 객체 null로 설정
        if (binding != null) {
            binding = null;
        }

        // SpannableStringBuilder 객체 null로 설정
        if (ssb != null) {
            ssb.clear();  // 사용된 텍스트 데이터를 정리
            ssb = null;
        }

        // errorText 리스트 null로 설정
        if (errorText != null) {
            errorText.clear();  // 리스트 안의 요소들을 정리
            errorText = null;
        }
    }
    }